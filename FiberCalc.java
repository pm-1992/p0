import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

class FiberCalc {
    public static void main(String[] args) throws MalformedURLException, IOException{  
        String foodInput = args[0]; //Stores the user's desired food input
        HttpInterface connOne = new HttpInterface();  
        URL conUrl = new URL(connOne.appendAccessPoint(foodInput)); //Creates the HTTP requests using the food input
        HttpURLConnection con = (HttpURLConnection) conUrl.openConnection(); //Opens the connection using the generated URL
        con.setRequestMethod("GET"); //Sets the request method for the connection
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream())); //Prepares the input stream reader for the HTTP response
        String inputLine; 
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) { //loops through the response input stream and adds them to the StringBuffer
            content.append(inputLine);
        }
        in.close(); 
        con.disconnect();  
        String fiber;  
        int index2 = 0;  
        //I was having trouble importing a package to parse JSON, so I did some silly parsing myself below. This can be improved upon greatly
        int index = content.indexOf("FIBTG"); //Finds the fiber entry for the top result from the search for the food input. 
        while (content.charAt(index) != ':'){ //Gets the index of the first colon
            index++;
        }  
        index++; 
        while (content.charAt(index) == ' '){  //Gets the index of the start of the fiber value
            index++;
        }     
        index2 = index;
        while (content.charAt(index2) != ' '){  //Gets the index of the end of the fiber value
            index2++;
        }   
        CharSequence result = content.subSequence(index, index2); //Creates a charsequence substring of only the numerical fiber value
        fiber = result.toString();  
        System.out.println("One serving of " + foodInput + " contains " + fiber + " grams of fiber.");
    } 
} 

class HttpInterface { 
    public static String app_id = "?app_id=b6f24731"; 
    public static String app_key = "&app_key=bcceec77e2f81e90803fbd92f2aad235";  
    public static String parserAccessPoint = "https://api.edamam.com/api/food-database/v2/parser"; 
    public static String getAppId(){ 
        return app_id;
    } 
    public static String getAppKey(){ 
        return app_key;
    } 
    public  String appendAccessPoint(String input){  //Builds the request URL using the food input
        String Result = parserAccessPoint + app_id + app_key + "&ingr=" + input + "&nutrition-type=logging";
        
        return Result;

    }
}
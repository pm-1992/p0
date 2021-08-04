import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

class HttpInterface2 {  
    
    public static String app_id = "?app_id=b6f24731"; 
    public static String app_key = "&app_key=bcceec77e2f81e90803fbd92f2aad235";  
    public static String parserAccessPoint = "https://api.edamam.com/api/food-database/v2/parser"; 
    public static String nutrientsAccessPoint = "https://api.edamam.com/api/food-database/v2/nutrients?app_id=b6f24731&app_key=bcceec77e2f81e90803fbd92f2aad235";  
    
   
    public  static String appendAccessPoint(String input){  //Builds the request URL using the food input
        String Result = parserAccessPoint + app_id + app_key + "&ingr=" + input + "&nutrition-type=cooking";      
        return Result;

    }
    public static StringBuffer sendHttp( String input) throws MalformedURLException, IOException
    { 
        String urlInput = HttpInterface2.appendAccessPoint(input);
        URL conUrl = new URL(urlInput); //Creates the HTTP requests using the food input
        HttpURLConnection con = (HttpURLConnection) conUrl.openConnection(); //Opens the connection using the generated URL
        con.setRequestMethod("GET"); //Sets the request method for the connection
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream())); //Prepares the input stream reader for the HTTP response 
        StringBuffer content = getResponse(in);
        con.disconnect();   
        return content;
    } 

    public static StringBuffer postHttp(String input) throws IOException{   //Sends the POST request with the user's inputs in a JSON file, and then gets the final JSON response from the API
        URL conUrl = new URL(nutrientsAccessPoint); 
        HttpURLConnection con = (HttpURLConnection) conUrl.openConnection(); 
        con.setRequestMethod("POST"); 
        con.setRequestProperty("Content-Type", "application/json"); 
        con.setRequestProperty("Accept", "application/json"); 
        con.setDoOutput(true);
        DataOutputStream os = new DataOutputStream(con.getOutputStream());
        os.writeBytes(input); //Puts the JSON string in the output stream of connection
        os.flush();
        os.close(); 
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream())); //Get the final JSON response
        StringBuffer result = getResponse(in);   
        con.disconnect(); 
        return result;
    }

    public static StringBuffer getResponse(BufferedReader in) throws IOException{     //Gets an HTTP response and puts it in a StringBuffer object
        String inputLine;        
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) { //loops through the response input stream and adds them to the StringBuffer
            content.append(inputLine);
        }
        in.close(); 
         
        return content;
    }

}

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

class HttpInterface2 {  
    
    public static String app_id = "?app_id=b6f24731"; 
    public static String app_key = "&app_key=bcceec77e2f81e90803fbd92f2aad235";  
    public static String parserAccessPoint = "https://api.edamam.com/api/food-database/v2/parser";  
   
    
    public static String getAppId(){ 
        return app_id;
    } 
    public static String getAppKey(){ 
        return app_key;
    } 
    public  static String appendAccessPoint(String input){  //Builds the request URL using the food input
        String Result = parserAccessPoint + app_id + app_key + "&ingr=" + input + "&nutrition-type=logging";
        
        return Result;

    }
    public static StringBuffer sendHttp( String urlInput) throws MalformedURLException, IOException
    { 
       // HttpInterface2 connOne = new HttpInterface2();  
        URL conUrl = new URL(urlInput); //Creates the HTTP requests using the food input
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
        return content;
    } 



}

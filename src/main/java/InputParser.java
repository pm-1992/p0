import java.io.IOException;
import java.util.Scanner;

public class InputParser {  //Parser class to parse inputs and allow for multi-word searches into the food database by removing spaces from the input string

    public static String original = null; 
    public static void collectInput(){     //Gets the user input
        System.out.println("Enter a food or meal below:");
        Scanner in = new Scanner(System.in);   
        original = in.nextLine();   
    } 

    public static String parseInput(String input) throws IOException { //Replaces all spaces in the string with the %20 string, prepares the string to be used in a URI
        
        StringBuffer result  = new StringBuffer(input); 
        int index2 = 0;  
        while (index2 < result.length()) 
        {    
            if (result.charAt(index2) == ' '){ 
                result.setCharAt( index2, '%');  
                result.insert( (index2 + 1), "20");
                index2 = index2 + 3;
            } 
            else{ 
                index2++;
            }
        }   
        
        return result.toString();
    } 

    public static String createParsedFood() throws IOException{  //Gets the user input and parses it
        
        collectInput(); 
        String result = parseInput(original);
        return result; 
    }
    
}

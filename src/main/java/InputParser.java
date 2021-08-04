import java.io.IOException;
import java.util.Scanner;

public class InputParser {  //Parser class to parse inputs and allow for multi-word searches into the food database

    public static String original = null; 
    public static void collectInput(){   
        System.out.println("Enter a food or meal below:");
        Scanner in = new Scanner(System.in);   
        original = in.nextLine();   
        //in.close(); 
    } 

    public static String parseInput(String input) throws IOException { 
        
        //Scanner in = new Scanner(System.in);   
        //original = in.nextLine(); 
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

    public static String createParsedFood() throws IOException{ 
        
        collectInput(); 
        String result = parseInput(original);
        return result; 
    }
    
}

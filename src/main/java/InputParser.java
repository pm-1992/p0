import java.io.IOException;
import java.util.Scanner;

public class InputParser {  

    public static String original = null;
    public static String parseInput() throws IOException { 
        
        Scanner in = new Scanner(System.in);   
        original = in.nextLine(); 
        StringBuffer result  = new StringBuffer(original); 
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
        in.close();
        return result.toString();
    }
    
}

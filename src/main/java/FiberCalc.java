
import java.io.IOException;
import java.net.MalformedURLException;


class FiberCalc {
    public static void main(String[] args) throws MalformedURLException, IOException{  
        String foodInput = args[0]; //Stores the user's desired food input
        String fiber = BasicParser.fiberParse(HttpInterface2.sendHttp(foodInput));  
        if (fiber == "Code Red") 
            return; 
        else
            System.out.println("One serving of " + foodInput + " contains " + fiber + " grams of fiber."); 
    } 
} 


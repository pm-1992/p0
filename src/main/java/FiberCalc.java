
import java.io.IOException;
import java.net.MalformedURLException;


class FiberCalc {
    public static void main(String[] args) throws MalformedURLException, IOException{   
        driver driver = new driver(); 
        driver.parseResponse(HttpInterface2.sendHttp(InputParser.createParsedFood())); 
        driver.showOptions();  
        //driver.showOptions();
       /* String fiber = BasicParser.fiberParse(HttpInterface2.sendHttp(InputParser.createParsedFood())); 
        if (fiber == "Code Red") 
            return; 
        else
            System.out.println("One serving of " + InputParser.original + " contains " + fiber + " grams of fiber."); */
    } 
} 


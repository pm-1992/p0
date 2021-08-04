import org.junit.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.net.MalformedURLException;

public class TestDriver { 
    @Test
    public void appendAccessPointTest() {
        String correct = "https://api.edamam.com/api/food-database/v2/parser?app_id=b6f24731&app_key=bcceec77e2f81e90803fbd92f2aad235&ingr=test&nutrition-type=cooking"; 
        String actual = HttpInterface2.appendAccessPoint("test");
        assertEquals(correct, actual);
    } 

    @Test
    public void sendHttpTest() throws MalformedURLException, IOException { 
        StringBuffer result = null; 
        result = HttpInterface2.sendHttp("test"); 
        assertNotNull(result);
    } 

    @Test
    public void postHttpTest() throws MalformedURLException, IOException {  
        StringBuffer result = null;   
        float input = (float) 2.0;
        jsonOutput out = new jsonOutput(); 
        out.ingredients[0].setMeasure("http://www.edamam.com/ontologies/edamam.owl#Measure_unit"); 
        out.ingredients[0].setfoodId("food_bv3hog1bd5qa4oafi7lb3bjz8i92"); 
        out.ingredients[0].setQuantity(input); 
        driver tester = new driver(out); 
        result = HttpInterface2.postHttp(tester.createJson()); 
        assertNotNull(result);
    }

    @Test
    public void parserGoodInputsTest() throws MalformedURLException, IOException { 
        String actual = BasicParser.fiberParse(HttpInterface2.sendHttp("beef")); 
        String correct = "0.0";
        assertEquals(correct, actual);
    }
    
    @Test
    public void parserBadInputsTest() throws MalformedURLException, IOException { 
        String actual = BasicParser.fiberParse(HttpInterface2.sendHttp("xxkfaskfasfjhiw")); 
        String correct = "Code Red";
        assertEquals(correct, actual);
    } 

    @Test
    public void inputParserTest() throws IOException { 
        String actual = InputParser.parseInput("There should be no spaces left here"); 
        String correct = "There%20should%20be%20no%20spaces%20left%20here";
        assertEquals(correct, actual);
    }
}

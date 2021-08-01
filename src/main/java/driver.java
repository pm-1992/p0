import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class driver { 

    jsonHandler handler; 

    public void parseResponse(StringBuffer input) throws JsonMappingException, JsonProcessingException 
    { 
        ObjectMapper objectMapper = new ObjectMapper(); 
        //objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.handler = objectMapper.readValue (input.toString(), jsonHandler.class); 
    } 

    public void showOptions() 
    { 
        int index = ( this.handler.hints.length - 1 ); 
        for( int index2 = 0; index2 < index; index2++){    
            System.out.println((index2 + 1) + ")________________");
            System.out.println("Label: " + this.handler.hints[index2].food.label);  
            if (this.handler.hints[index2].food.brand != null) 
                System.out.println("Brand: " + this.handler.hints[index2].food.brand);
            if (this.handler.hints[index2].food.category != null) 
                System.out.println("Category: " + this.handler.hints[index2].food.category); 
            if (this.handler.hints[index2].food.categoryLabel != null) 
                System.out.println("Category label: " + this.handler.hints[index2].food.categoryLabel); 
            if (this.handler.hints[index2].food.foodContentsLabel != null) 
                System.out.println("Food contents label: " + this.handler.hints[index2].food.foodContentsLabel); 
            if (this.handler.hints[index2].food.image != null) 
                System.out.println("Image URL: " + this.handler.hints[index2].food.image);  
            if (this.handler.hints[index2].food.servingSizes != null){ 
                for( int index3 = 0; index3 < (this.handler.hints[index2].food.servingSizes.length - 1); index3++ ){ 
                    System.out.println("Serving unit: " + this.handler.hints[index2].food.servingSizes[index3].label); 
                    System.out.println("Quantity of units per serving: " + this.handler.hints[index2].food.servingSizes[index3].quantity);
                } 
            } 
            if (this.handler.hints[index2].food.servingsPerContainer != null) 
            System.out.println("Servings per container: " + this.handler.hints[index2].food.servingsPerContainer);
        }   
        
    }
    
}

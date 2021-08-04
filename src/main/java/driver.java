import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json4s.JsonInput;


public class driver {  //Class to handle the flow of the program and get inputs from the user

    jsonHandler handler;  //Class to hold the initial JSON response from the API
    jsonHandler2 handler2; //Class to hold the 2nd JSON response from the API
    static String foodSelection; //Holds the user's selected food
    int foodNum; //Holds the user's selected food's index in the hints array
    public String URI; //
    String measureLabel; 
    public jsonOutput out;  

    public driver(){  //Instantiates object for creating JSON output
        out = new jsonOutput();
    }

    public driver(jsonOutput input){ 
        out = input; 
        out.ingredients[0] = input.ingredients[0];
    }
    
    public void run() throws JsonMappingException, JsonProcessingException, MalformedURLException, IOException{  //Goes through the basic flow of the program to make a nutrition query from the Edamam food API
        this.parseResponse(HttpInterface2.sendHttp(InputParser.createParsedFood())); //Gets and parses the user input, sends the first HTTP GET to the API, and parses the JSON response 
        if (this.handler.hints.length == 0){   
            System.err.println("That food item was not found!");
            return; 
        }
        this.showOptions();   //Shows the user a list of the top 20 results from the API parser for their query
        this.collectFoodInput(); //Gets the user's selection among the top 20 results
        this.showSelection(); //Shows more specific information, gives the user available measurement units to select,
        this.measurementSelection(); //Gets the user's preferred measurement unit
        this.getQuantity();   //Gets the quantity of that measurement unit the user wants nutrition information for
        this.parseResponse2(HttpInterface2.postHttp(this.createJson())); //Creates the JSON output using the information gathered from the user above, sends an HTTP POST to the API, and returns specific nutrition information
        this.writeToFile(); //Writes the final requested nutrition information to file
    } 
    public void parseResponse(StringBuffer input) throws JsonMappingException, JsonProcessingException 
    {  // Parses the first JSON response
        ObjectMapper objectMapper = new ObjectMapper(); 
        this.handler = objectMapper.readValue (input.toString(), jsonHandler.class); 
    } 

    //Parses the second JSON response
    public void parseResponse2(StringBuffer input) throws JsonMappingException, JsonProcessingException            
    {  
        ObjectMapper objectMapper = new ObjectMapper();                
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); //Ignores configuration data at the end that the user does not need
        this.handler2 = objectMapper.readValue (input.toString(), jsonHandler2.class); 
    } 

    public void showOptions()  //Iterates through the top 20 results returned by the parser API and displays them with some information 
    { 
        int index = ( this.handler.hints.length ); 
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
                for( int index3 = 0; index3 < (this.handler.hints[index2].food.servingSizes.length ); index3++ ){ 
                    System.out.println("Serving unit: " + this.handler.hints[index2].food.servingSizes[index3].label); 
                    System.out.println("Quantity of units per serving: " + this.handler.hints[index2].food.servingSizes[index3].quantity);
                } 
            } 
            if (this.handler.hints[index2].food.servingsPerContainer != null) 
            System.out.println("Servings per container: " + this.handler.hints[index2].food.servingsPerContainer);
        }   
        
    }
    
    public void collectFoodInput(){   //Gets the user's desired food selection
        System.out.println("Enter the number that best matches your desired food or meal:");
        Scanner scan = new Scanner(System.in);   
        foodSelection = scan.nextLine();   
         
    }  

    public void showSelection(){  //Shows detailed information about the user's food selection
        Integer index = (Integer.decode (foodSelection) - 1);   
        foodNum = index; 
        this.out.ingredients[0].setfoodId(this.handler.hints[index].food.foodId);
        System.out.println("Label: " + this.handler.hints[index].food.label);    
            if (this.handler.hints[index].food.brand != null) 
                System.out.println("Brand: " + this.handler.hints[index].food.brand);
            if (this.handler.hints[index].food.category != null) 
                System.out.println("Category: " + this.handler.hints[index].food.category); 
            if (this.handler.hints[index].food.categoryLabel != null) 
                System.out.println("Category label: " + this.handler.hints[index].food.categoryLabel); 
            if (this.handler.hints[index].food.foodContentsLabel != null) 
                System.out.println("Food contents label: " + this.handler.hints[index].food.foodContentsLabel); 
            if (this.handler.hints[index].food.image != null) 
                System.out.println("Image URL: " + this.handler.hints[index].food.image);  
            if (this.handler.hints[index].food.servingSizes != null){ 
                for( int index3 = 0; index3 < (this.handler.hints[index].food.servingSizes.length); index3++ ){ 
                    System.out.println("Serving unit: " + this.handler.hints[index].food.servingSizes[index3].label); 
                    System.out.println("Quantity of units per serving: " + this.handler.hints[index].food.servingSizes[index3].quantity);
                } 
            } 
            if (this.handler.hints[index].food.servingsPerContainer != null) 
                System.out.println("Servings per container: " + this.handler.hints[index].food.servingsPerContainer);
        if (this.handler.hints[index].measures != null){ 
            
            for( int index2 = 0; index2 < (this.handler.hints[index].measures.length ); index2++){   //Iterates and displays all measurement units for user selection
                System.out.println("Measure #" + (index2 + 1));
                System.out.println("    Label: " + this.handler.hints[index].measures[index2].label);  
                System.out.println("    Weight: " + this.handler.hints[index].measures[index2].weight + " grams"); 
            }
        }   
    } 

   
    public void measurementSelection(){  //Gets the user's measurement unit selection
        System.out.println("Enter the number for the measurement unit you would like to use:");  
        Scanner scan = new Scanner(System.in);   
        String measure = scan.nextLine();   
        Integer index = (Integer.decode (measure) - 1) ;   
        this.out.ingredients[0].setMeasure(this.handler.hints[foodNum].measures[index].uri);   
        measureLabel = this.handler.hints[foodNum].measures[index].label;
        
    } 

    
    //Gets the user's quantity selection 
    public void getQuantity(){ 
        System.out.println("Enter how many " + measureLabel.toLowerCase() +"s of " + this.handler.hints[foodNum].food.label + " you want nutrition information for:");
        Scanner scan = new Scanner(System.in);  
        Float selection = Float.parseFloat(scan.nextLine());
        this.out.ingredients[0].setQuantity(selection);
        scan.close();
    } 

    
    //Creates JSON string for output using user inputs
    public String createJson() throws JsonProcessingException{ 
        ObjectMapper objectMapper = new ObjectMapper();  
        String result = objectMapper.writeValueAsString(out); 
        return result;
    } 

    //Writes all available nutrition information for the user's selected food, unit and quantity to file
    public void writeToFile() throws IOException{ 
        File output = new File("NutritionInformation.txt"); 
        FileWriter writer = new FileWriter("NutritionInformation.txt");    
        output.createNewFile();
        writer.write("Nutrition information for " + this.out.ingredients[0].quantity + " " + measureLabel.toLowerCase() +"s of " + this.handler.hints[foodNum].food.label + "\n"); 
        writer.write("=============================================================================" + "\n");   
        if(handler2.dietLabels.length > 0){
            writer.write("Diet Labels" + "\n");
            for (int i = 0; i < handler2.dietLabels.length; i++) 
                writer.write(handler2.dietLabels[i]+ "\n");  
        } 
        if(handler2.healthLabels.length > 0){ 
            writer.write("Health Labels" + "\n");
            for (int i = 0; i < handler2.healthLabels.length; i++) 
                writer.write(handler2.healthLabels[i] + "\n");  
        }   
        if(handler2.cautions.length > 0){ 
            writer.write("Cautions" + "\n");
            for (int i = 0; i < handler2.cautions.length; i++) 
                writer.write(handler2.cautions[i] + "\n");  
        }   
        writer.write("Total Nutrition Values" + "\n");  
        if (handler2.totalNutrients.ENERC_KCAL != null)
            writer.write(handler2.totalNutrients.ENERC_KCAL.label + ": " + handler2.totalNutrients.ENERC_KCAL.quantity + " " + handler2.totalNutrients.ENERC_KCAL.unit + "\n");  
        if (handler2.totalNutrients.FAT != null)
            writer.write(handler2.totalNutrients.FAT.label + ": " + handler2.totalNutrients.FAT.quantity + " " + handler2.totalNutrients.FAT.unit + "\n");  
        if (handler2.totalNutrients.FASAT != null)
            writer.write(handler2.totalNutrients.FASAT.label + ": " + handler2.totalNutrients.FASAT.quantity + " " + handler2.totalNutrients.FASAT.unit + "\n");  
        if (handler2.totalNutrients.FAMS != null)
            writer.write(handler2.totalNutrients.FAMS.label + ": " + handler2.totalNutrients.FAMS.quantity + " " + handler2.totalNutrients.FAMS.unit + "\n");  
        if (handler2.totalNutrients.FAPU != null)
            writer.write(handler2.totalNutrients.FAPU.label + ": " + handler2.totalNutrients.FAPU.quantity + " " + handler2.totalNutrients.FAPU.unit + "\n");  
        if (handler2.totalNutrients.CHOCDF != null)
            writer.write(handler2.totalNutrients.CHOCDF.label + ": " + handler2.totalNutrients.CHOCDF.quantity + " " + handler2.totalNutrients.CHOCDF.unit + "\n");  
        if (handler2.totalNutrients.FIBTG != null)
            writer.write(handler2.totalNutrients.FIBTG.label + ": " + handler2.totalNutrients.FIBTG.quantity + " " + handler2.totalNutrients.FIBTG.unit + "\n");  
        if (handler2.totalNutrients.SUGAR != null)
            writer.write(handler2.totalNutrients.SUGAR.label + ": " + handler2.totalNutrients.SUGAR.quantity + " " + handler2.totalNutrients.SUGAR.unit + "\n");  
        if (handler2.totalNutrients.PROCNT != null)
            writer.write(handler2.totalNutrients.PROCNT.label + ": " + handler2.totalNutrients.PROCNT.quantity + " " + handler2.totalNutrients.PROCNT.unit + "\n");  
        if (handler2.totalNutrients.CHOLE != null)
            writer.write(handler2.totalNutrients.CHOLE.label + ": " + handler2.totalNutrients.CHOLE.quantity + " " + handler2.totalNutrients.CHOLE.unit + "\n");  
        if (handler2.totalNutrients.NA != null)
            writer.write(handler2.totalNutrients.NA.label + ": " + handler2.totalNutrients.NA.quantity + " " + handler2.totalNutrients.NA.unit + "\n");  
        if (handler2.totalNutrients.CA != null)
            writer.write(handler2.totalNutrients.CA.label + ": " + handler2.totalNutrients.CA.quantity + " " + handler2.totalNutrients.CA.unit + "\n");  
        if (handler2.totalNutrients.MG != null)
            writer.write(handler2.totalNutrients.MG.label + ": " + handler2.totalNutrients.MG.quantity + " " + handler2.totalNutrients.MG.unit + "\n");  
        if (handler2.totalNutrients.K != null)
            writer.write(handler2.totalNutrients.K.label + ": " + handler2.totalNutrients.K.quantity + " " + handler2.totalNutrients.K.unit + "\n");  
        if (handler2.totalNutrients.FE != null)
            writer.write(handler2.totalNutrients.FE.label + ": " + handler2.totalNutrients.FE.quantity + " " + handler2.totalNutrients.FE.unit + "\n");  
        if (handler2.totalNutrients.ZN != null)
            writer.write(handler2.totalNutrients.ZN.label + ": " + handler2.totalNutrients.ZN.quantity + " " + handler2.totalNutrients.ZN.unit + "\n");  
        if (handler2.totalNutrients.P != null)
            writer.write(handler2.totalNutrients.P.label + ": " + handler2.totalNutrients.P.quantity + " " + handler2.totalNutrients.P.unit + "\n");  
        if (handler2.totalNutrients.VITA_RAE != null)
            writer.write(handler2.totalNutrients.VITA_RAE.label + ": " + handler2.totalNutrients.VITA_RAE.quantity + " " + handler2.totalNutrients.VITA_RAE.unit + "\n"); 
        if (handler2.totalNutrients.VITC != null)
            writer.write(handler2.totalNutrients.VITC.label + ": " + handler2.totalNutrients.VITC.quantity + " " + handler2.totalNutrients.VITC.unit + "\n");  
        if (handler2.totalNutrients.THIA != null)
            writer.write(handler2.totalNutrients.THIA.label + ": " + handler2.totalNutrients.THIA.quantity + " " + handler2.totalNutrients.THIA.unit + "\n");  
        if (handler2.totalNutrients.RIBF != null)
            writer.write(handler2.totalNutrients.RIBF.label + ": " + handler2.totalNutrients.RIBF.quantity + " " + handler2.totalNutrients.RIBF.unit + "\n");  
        if (handler2.totalNutrients.NIA != null)
            writer.write(handler2.totalNutrients.NIA.label + ": " + handler2.totalNutrients.NIA.quantity + " " + handler2.totalNutrients.NIA.unit + "\n");  
        if (handler2.totalNutrients.VITB6A != null)
            writer.write(handler2.totalNutrients.VITB6A.label + ": " + handler2.totalNutrients.VITB6A.quantity + " " + handler2.totalNutrients.VITB6A.unit + "\n");  
        if (handler2.totalNutrients.FOLDFE != null)
            writer.write(handler2.totalNutrients.FOLDFE.label + ": " + handler2.totalNutrients.FOLDFE.quantity + " " + handler2.totalNutrients.FOLDFE.unit + "\n");  
        if (handler2.totalNutrients.FOLFD != null)
            writer.write(handler2.totalNutrients.FOLFD.label + ": " + handler2.totalNutrients.FOLFD.quantity + " " + handler2.totalNutrients.FOLFD.unit + "\n");  
        if (handler2.totalNutrients.FOLAC != null)
            writer.write(handler2.totalNutrients.FOLAC.label + ": " + handler2.totalNutrients.FOLAC.quantity + " " + handler2.totalNutrients.FOLAC.unit + "\n");  
        if (handler2.totalNutrients.VITB12 != null)
            writer.write(handler2.totalNutrients.VITB12.label + ": " + handler2.totalNutrients.VITB12.quantity + " " + handler2.totalNutrients.VITB12.unit + "\n");  
        if (handler2.totalNutrients.VITD != null)
            writer.write(handler2.totalNutrients.VITD.label + ": " + handler2.totalNutrients.VITD.quantity + " " + handler2.totalNutrients.VITD.unit + "\n");  
        if (handler2.totalNutrients.TOCPHA != null)
            writer.write(handler2.totalNutrients.TOCPHA.label + ": " + handler2.totalNutrients.TOCPHA.quantity + " " + handler2.totalNutrients.TOCPHA.unit + "\n");  
        if (handler2.totalNutrients.VITK1 != null)
            writer.write(handler2.totalNutrients.VITK1.label + ": " + handler2.totalNutrients.VITK1.quantity + " " + handler2.totalNutrients.VITK1.unit + "\n");  
        if (handler2.totalNutrients.WATER != null)
            writer.write(handler2.totalNutrients.WATER.label + ": " + handler2.totalNutrients.WATER.quantity + " " + handler2.totalNutrients.WATER.unit + "\n"); 

        writer.write("Percentage of Daily Totals" + "\n");   
        if (handler2.totalDaily.ENERC_KCAL != null)
            writer.write(handler2.totalDaily.ENERC_KCAL.label + ": " + handler2.totalDaily.ENERC_KCAL.quantity + " " + handler2.totalDaily.ENERC_KCAL.unit + "\n");  
        if (handler2.totalDaily.FAT != null)
            writer.write(handler2.totalDaily.FAT.label + ": " + handler2.totalDaily.FAT.quantity + " " + handler2.totalDaily.FAT.unit + "\n");  
        if (handler2.totalDaily.FASAT != null)
            writer.write(handler2.totalDaily.FASAT.label + ": " + handler2.totalDaily.FASAT.quantity + " " + handler2.totalDaily.FASAT.unit + "\n");  
        if (handler2.totalDaily.CHOCDF != null)
            writer.write(handler2.totalDaily.CHOCDF.label + ": " + handler2.totalDaily.CHOCDF.quantity + " " + handler2.totalDaily.CHOCDF.unit + "\n");  
        if (handler2.totalDaily.FIBTG != null)
            writer.write(handler2.totalDaily.FIBTG.label + ": " + handler2.totalDaily.FIBTG.quantity + " " + handler2.totalDaily.FIBTG.unit + "\n");  
        if (handler2.totalDaily.SUGAR != null)
            writer.write(handler2.totalDaily.SUGAR.label + ": " + handler2.totalDaily.SUGAR.quantity + " " + handler2.totalDaily.SUGAR.unit + "\n");  
        if (handler2.totalDaily.PROCNT != null)
            writer.write(handler2.totalDaily.PROCNT.label + ": " + handler2.totalDaily.PROCNT.quantity + " " + handler2.totalDaily.PROCNT.unit + "\n");  
        if (handler2.totalDaily.CHOLE != null)
            writer.write(handler2.totalDaily.CHOLE.label + ": " + handler2.totalDaily.CHOLE.quantity + " " + handler2.totalDaily.CHOLE.unit + "\n");  
        if (handler2.totalDaily.NA != null)
            writer.write(handler2.totalDaily.NA.label + ": " + handler2.totalDaily.NA.quantity + " " + handler2.totalDaily.NA.unit + "\n");  
        if (handler2.totalDaily.CA != null)
            writer.write(handler2.totalDaily.CA.label + ": " + handler2.totalDaily.CA.quantity + " " + handler2.totalDaily.CA.unit + "\n");  
        if (handler2.totalDaily.MG != null)
            writer.write(handler2.totalDaily.MG.label + ": " + handler2.totalDaily.MG.quantity + " " + handler2.totalDaily.MG.unit + "\n");  
        if (handler2.totalDaily.K != null)
            writer.write(handler2.totalDaily.K.label + ": " + handler2.totalDaily.K.quantity + " " + handler2.totalDaily.K.unit + "\n");   
        if (handler2.totalDaily.FE != null)
            writer.write(handler2.totalDaily.FE.label + ": " + handler2.totalDaily.FE.quantity + " " + handler2.totalDaily.FE.unit + "\n"); 
        if (handler2.totalDaily.ZN != null) 
            writer.write(handler2.totalDaily.ZN.label + ": " + handler2.totalDaily.ZN.quantity + " " + handler2.totalDaily.ZN.unit + "\n");  
        if (handler2.totalDaily.P != null)
            writer.write(handler2.totalDaily.P.label + ": " + handler2.totalDaily.P.quantity + " " + handler2.totalDaily.P.unit + "\n");  
        if (handler2.totalDaily.VITA_RAE != null)
            writer.write(handler2.totalDaily.VITA_RAE.label + ": " + handler2.totalDaily.VITA_RAE.quantity + " " + handler2.totalDaily.VITA_RAE.unit + "\n"); 
        if (handler2.totalDaily.VITC != null)
            writer.write(handler2.totalDaily.VITC.label + ": " + handler2.totalDaily.VITC.quantity + " " + handler2.totalDaily.VITC.unit + "\n");  
        if (handler2.totalDaily.THIA != null)
            writer.write(handler2.totalDaily.THIA.label + ": " + handler2.totalDaily.THIA.quantity + " " + handler2.totalDaily.THIA.unit + "\n");  
        if (handler2.totalDaily.RIBF != null)
            writer.write(handler2.totalDaily.RIBF.label + ": " + handler2.totalDaily.RIBF.quantity + " " + handler2.totalDaily.RIBF.unit + "\n");  
        if (handler2.totalDaily.NIA != null)
            writer.write(handler2.totalDaily.NIA.label + ": " + handler2.totalDaily.NIA.quantity + " " + handler2.totalDaily.NIA.unit + "\n");  
        if (handler2.totalDaily.VITB6A != null)
            writer.write(handler2.totalDaily.VITB6A.label + ": " + handler2.totalDaily.VITB6A.quantity + " " + handler2.totalDaily.VITB6A.unit + "\n");  
        if (handler2.totalDaily.FOLDFE != null)
            writer.write(handler2.totalDaily.FOLDFE.label + ": " + handler2.totalDaily.FOLDFE.quantity + " " + handler2.totalDaily.FOLDFE.unit + "\n");  
        if (handler2.totalDaily.VITB12 != null)
            writer.write(handler2.totalDaily.VITB12.label + ": " + handler2.totalDaily.VITB12.quantity + " " + handler2.totalDaily.VITB12.unit + "\n");  
        if (handler2.totalDaily.VITD != null)
            writer.write(handler2.totalDaily.VITD.label + ": " + handler2.totalDaily.VITD.quantity + " " + handler2.totalDaily.VITD.unit + "\n");  
        if (handler2.totalDaily.TOCPHA != null)
            writer.write(handler2.totalDaily.TOCPHA.label + ": " + handler2.totalDaily.TOCPHA.quantity + " " + handler2.totalDaily.TOCPHA.unit + "\n"); 
        if (handler2.totalDaily.VITK1 != null) 
            writer.write(handler2.totalDaily.VITK1.label + ": " + handler2.totalDaily.VITK1.quantity + " " + handler2.totalDaily.VITK1.unit + "\n"); 
        writer.close();
    }

}

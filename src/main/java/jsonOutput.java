public class jsonOutput {  //Class for building the JSON output to be sent to the API
    public ingredients[] ingredients;  
    public jsonOutput(){  
        this.ingredients = new ingredients[1];  
        ingredients[0] = new ingredients();    //Instantiates array and first index ingredient object
        
    }
}

public class ingredients { //Class for building JSON output from user inputs
    public float quantity; 
    public String measureURI; 
    public String foodId; 

    public void setQuantity(Float input){ 
        quantity = input;
    } 
    public void setMeasure(String input){ 
        measureURI = input;
    } 
    public void setfoodId(String input){ 
        foodId = input;
    } 
}


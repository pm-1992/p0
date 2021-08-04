class BasicParser{ //Just gets the fiber value for one serving, no longer used in main flow of program
    public static String fiberParse (StringBuffer response) {  
        String fiber = " ";
        int index2 = 0;  
        int index = 0;  
        try { 
            index = response.indexOf("FIBTG");  
            while (response.charAt(index) != ':' ){ //Gets the index of the first colon
                index++; 
            }  
            index++; 
            while (response.charAt(index) == ' '){  //Gets the index of the start of the fiber value
                index++;
            }     
            index2 = index;
            while (response.charAt(index2) != ' '){  //Gets the index of the end of the fiber value
                index2++;
            }    
            CharSequence result = response.subSequence(index, index2); //Creates a charsequence substring of only the numerical fiber value
            fiber = result.toString();   
        }       //Finds the fiber entry for the top result from the search for the food input.  
        catch(Exception test){ 
            System.err.println("Food not found! Please try a different string."); 
            fiber = "Code Red";
        }
        return fiber; 

    }
    

}
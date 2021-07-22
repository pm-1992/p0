#Fiber Calculator 
- This app will take a food or ingredient input via command line argument, 
query the Edamam food database API, and then 
display the amount of fiber that one serving 
of that food's top search result contains. 
- Developed and functional features:
	- Enter a food and get the top result's fiber value returned in grams
- Features still in development: 
	- The ability to enter a string of more than one word 
	- The ability of the user to specify the units and amount of their desired ingredient 
	- The ability to specify and return any food or nutritional data, not just fiber values 
	- The ability to parse the servers JSON response and store all of the data effectively  
	- A local SQLite database that logs the responses and results of each query, keeps persistent data
	- The ability to display the search results and allow the user to select the closest result

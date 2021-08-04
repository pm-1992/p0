#Fiber Calculator

This app will take a food or ingredient input via command line argument, query the Edamam food database API, and then display the the top 20 results based on the user's input. Next, it gets the users desired food selection, quantity, and measurement unit. This information is sent to the
API and detailed full nutrition information is returned, which is parsed and returned to file.

Developed and functional features:

Enter a food and get the top result's fiber value returned in grams
The ability to enter a string of more than one word
The ability of the user to specify the units and amount of their desired ingredient
The ability to return any food or nutritional data, not just fiber values
The ability to parse the servers JSON response and store all of the data effectively
The ability to display the search results and allow the user to select the closest result
JUnit unit tests
Features still in development:

A local SQLite database that logs the responses and results of each query, keeps persistent data
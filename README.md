CHP_Project_TravelGood
======================

A set of web services for a travel agency web application. Project work for 02267 Software Development of Web Services E12 that took place in Fall 2012 at DTU.

How-to:


	Service project:
	 - check out the code from the repository
	 - open Netbeans and open the desired projects
	 - for the *Service projects, expand "Web Services" folder, right-click on the web service and click "Refresh Service"
	 - Uncheck "Also re-generate implementation class" !!!
	 - Check "Also replace local wsdl file with the original one .... "
	 - Right click on the project, select "Clean and build"
	 - When done, right-click on the project, select "Deploy"
	 
	If that does not help:
	 - When refreshing the service, check the "Also replace local wsdl file with the original one .... "
	 - Edit the path in the box below to reflect the actual position of your WSDL file
	 - Keep the "Also re-generate implementation class" UNCHECKED!
	 
	 Worst-case scenario (applies only to Service projects):
	 - Backup (copy to notepad or sth) the contents of your *Service.java file (the one containing web service implementation)
	 	(WARNING - your *Service.java file will disappear after the you complete the next step!)
	 - Expand your project, expand "Web Service References", right-click on the reference and click "Delete"
	 - Right-click on the project, and select "New -> Web Service from WSDL.."
	 - Pick our WSDL file and create the webservice
	 - Once the new java file is created, open it and replace the dummy methods with the ones you have in your backup (and only methods)
	 - Clean & Build, you should be good to go
	 
	 
	 
	 Client (test) project:
	 - first, make sure your service is deployed (if not, use the previous instruction)
	 - Expand your project, expand "Web Service References", right-click on the reference and click "Refresh Client"
	 - Check "Also replace local wsdl file with the original one .... " !!! 
	 - Right click on the project, select "Clean and build"
	 - Run your tests, they should work fine now
 
# app.demo
App developed by Wenderson Ozorio de Souza

Follow all step in order to run the application.

Download and unzip the source repository for this guide, or clone it using Git: git clone https://github.com/wendersonsouza/app.demo

cd into app.demo

run the commands below: 

		mvn clean install 
		java -jar -Dserver.port=<port number> target/app.demo-0.0.1-SNAPSHOT.jar 
		
after that assuming the 8080 has been set up.
access http://localhost:8080/

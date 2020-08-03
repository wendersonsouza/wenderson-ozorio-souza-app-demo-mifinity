# app.demo
App developed by Wenderson Ozorio de Souza

Follow all step in order to run the application.

Download and unzip the source repository for this guide, or clone it using Git: git clone https://github.com/wendersonsouza/wenderson-ozorio-souza-app-demo-mifinity.git

cd into wenderson-ozorio-souza-app-demo-mifinity

run the commands below: 

		mvn clean install 
		java -jar -Dserver.port=8080 target/app.demo.mifinity-0.0.1-SNAPSHOT.jar 
		
after that assuming the 8080 has been set up.
access http://localhost:8080/login.html

credentials:
Application login
	username: user01 password: user01
	
H2 Database (in memory) login
	url: http://localhost:8080/h2
	user: sa
	password: 

## intro
Lyrics Manager is a program consisting of two modules spring-backend and javafx-front where x spring-backend
is a spring-boot application containing logic for the lyrics server and the web part of the application
The javafx-front module is a desktop application and both modules functionality is described in the requirements 
as per the assignment tab for the respetive course (programming with frameworks and networking)

## PRE-REQUISITES 

-Java runtime environment

-maven locally installed and can be accessed from the terminal

-port 5000 should not be in use 



## TO RUN THE APPLICATION ON THE TERMINAL

1 go to the path name of module spring-backend and run the command:

              mvn spring-boot:run

2 open another instance of the terminal

3 go to the path of module javafx-front and run the following commands

              mvn clean install

              mvn compile

              mvn exec:java -D exec.mainClass="swe2040ProjectGUI.Main"

## TO ACCESS  THE WEB PART GO TO THIS URL:
          http://localhost:5000/getsongtitle

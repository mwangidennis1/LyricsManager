# Lyrics Manager
This project was developed as part of SWE2040 : Programming with Frameworks and Networking

It is a program consisting of two modules spring-backend and javafx-front

The spring-backend module acts contains the application server which contains the backend  logic and also a web-component

The javafx-front module is a desktop client  for the spring-backend  module which allows for creation and management of lyrics

The project allows for the creation of song lyrics from the desktop client and users can view song lyrics from the web component
## Features
 * CRUD operations on Lyrics
 * Web component to search for various song lyrics
 
## Installation 

 * Java runtime environment , JDK 17+
 * maven locally installed and can be accessed 
 * port 5000 should not be in use

To run the backend first:
```
  https://github.com/mwangidennis1/LyricsManager.git
  cd LyricsManager/spring-backend
  mvn clean install
  ```
 To run the desktop client:
 ```
  cd LyricsManager/javafx-front
  mvn clean install
  mvn compile
  ```

## Quickstart
To run the backend:
 ```
   mvn spring-boot:run
 ```
To run the client:
```
   mvn exec:java -D exec.mainClass="swe2040ProjectGUI.Main
 ```
To access the web-component:
          http://127.0.0.1:5000/getsongtitle

## configuration:
   The server ports can be changed if the port mentioned above is not free on both modules:
   ```
   server.port=your.port
   ```

## Contributor
Contributors:
   [@tedndege](https://github.com/tedndege)
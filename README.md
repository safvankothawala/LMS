# LMS Server App
This is a source code repository for LMS Server App.

LMS Server App is the central application used for management of Lottery Software Solution.

It publishes REST APIs which are consumed by the LMS Customer App. Following are the APIs published by the system:

* Create a Draw
* Generate Tickets for a Draw
* Create a License
* Create a Customer
* Create a Ticket Owner
* Purchase a Ticket
* Select a Winner Ticket
* Check for Winning Ticket for a Customer
* Check Customer App Version

### Technology Stack ###
* Java 17
* Spring Boot 3.1.2
* MySQL 5.7

### Build Steps ###
Run below command to build the application

**mvn clean install**

Run below command to run the test cases

**mvn test**

Run below command to run the application

**java - jar target/lms-1.0.jar**





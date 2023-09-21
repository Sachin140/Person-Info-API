# Spring Boot API for Person Info

## Overview

This Spring Boot API project provides a single endpoint that takes a random name of a person as input and returns the person's age, gender, and nationality. 
- It accomplishes this by utilizing external public APIs: 
  - [Agify](https://agify.io/) 
  - [Genderize](https://genderize.io/)
  - [Nationalize](https://nationalize.io/)

## Requirements

To run this project, you need the following:

- Java 17 or later
- Apache Maven

## Getting Started

To run the API, follow these steps:

1. Clone the repository

   ```shell
   git clone https://github.com/Sachin140/Person-Info-API.git
   cd Person-Info-API

2. Build the project using Maven:
   - mvn clean install
3. Run the application:
   - mvn spring-boot:run, it will pick default profile.
   - The API will be accessible at http://localhost:8080.

4. Endpoint
   - curl -X GET "http://localhost:8080/person-info?name=John"
6. Technologies Used
  - Java 17
  - Spring Boot (Reactive)
  - Maven
  - This API project uses the following public APIs to determine person information:
    - Agify
    - Genderize
    - Nationalize
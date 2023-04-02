# Store Management Tool POC
---
This POC is centered around the idea of a Store Management Tool developed as a Spring Boot application which can be run in Docker, along with the PostgreSQL DB setup. The application exposes APIs such as:
- Create Product
- Patch Product
- Get all Products
- Get Product by ID
- Delete Product by ID

## Project Setup
The project consists of two main components:
- Spring Boot service
- PostgreSQL DB

Both components are dockerized and the configuration can be found in ```docker_setup``` folder. After building the docker image of the service using the Dockerfile, image name should be placed inside the ```docker-compose.yml``` file in the place of ```store:localDocker``` from line no 24. After this step, the ```docker compose up``` command can be executed and 3 containers will start:
1. PostgreSQL DB on docker vm ip and port 5432 (ex. for WSL2 docker - http://172.21.97.86:5432)
2. pgAdmin4 on docker vm ip and port 5050 (ex. for WSL2 docker - http://172.21.97.86:5050) - username and password for pgAdmin can be found inside the ```docker-compose.yml``` file
3. Store Service on docker vm ip and port 5060 (ex. for WSL2 docker - http://172.21.97.86:5460)

## PostgreSQL DB
The database of this project consists in three tables:
- store_product
- store_manufacturer
- store_product_type

The dependencies between the tables, constraints and initial data can be found in the ```init.sql``` initialization file. Most importantly, between the ```store_product``` and the other two tables there is a many-to-one relationship based on the FK.

## Store Spring Boot Service
The main application is the Spring Boot store service. It acts as a management tool service, exposing APIs for managing the products from a store. All APIs have configured a basic authentication mechanism with a username and a password and the access is role based. A postman collection can be found under ```postman_collection```.
1. Create Product API - POST ```/api/v1/products``` - 
This API creates a new product into the database and based on the implemented error mechanism and logs, multiple outcomes might occur inside the response.
2. Patch Product API - PATCH ```/api/v1/products/{productId}``` - 
This API updates existing product's fields into the database and based on what properties are sent inside the request body, only those fields will be updated.
3. Get all products API - GET ```/api/v1/products``` - 
This API retrieves all existing products from the DB.
4. Get Product by ID API - GET ```/api/v1/products/{productId}``` - 
This API searches a product in DB after the ```productId``` and returns it.
5. Delete Product API - DELETE ```/api/v1/products/{productId}``` - 
This API deletes a product from the database.

All APIs have configured an error mechanism which might present different responses based on the encountered issue. Also, details can be found inside the logs which also have a ```correlationId``` set for each request for easy tracking.

## Azure Cloud Improvements
As this is a POC, the application lives only locally and all configurations are hardcoded inside the ```application.properties``` file. However, if deployed to the cloud, some improvements might be:
1. Deploy DB as postgreSQL DB resource and the service as an App Service resources inside the same Resource Group.
2. Move all sensitive data from ```application.properties``` to Azure Key Vault resource and using Spring Cloud libraries implement a mechanism inside the code for connecting to the KV using Managed Identities in order not to work with credentials and let Azure deal with associating the resources. 
3. Use an Application Configuration resource in order to store paths or other configurations.
4. Configure Log Analytics and get the logs sent to Application Insights where different queries can be performed for filtering the logs in order to identify different problems.
5. Configure and APIM to expose the APIs to external services.
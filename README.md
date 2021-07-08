# Client Service API Application
Client Service Application to handle client related data.

It is a Spring-Boot application written using CQRS pattern to separate command and query responsibility.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. 

**IMPORTANT**

To start of build this application using JDK 1.8 as this is my current office workstation JDK. 

Hence I did not want to mess it up right now as there are too many moving parts of old applications we maintain.

## Usage
To build the application and generate the open api definition:
```
chmod +x gradlew
./gradlew clean build generateOpenApiDocs
```

To run the server, execute these command from the root directory:
```
./gradlew bootRun
```

To check if the server is up:
```
curl http://localhost:8080/private/actuator/health
```

The OpenAPI definition is located here:
```
curl http://localhost:8080/client-api.yaml
```

The swagger.json file is located in build/docs/swagger.json

## Run with Docker

A simple dockerfile has been provided. To run it, simple follow the steps above to build the application and then do the steps below

```
docker compose build
```

Wait for the docker container to be created. 

```
docker compose up
```

If you have docker desktop, you can just click start on docker container name client-server

### Non Functional Requirements

1. Security 
- provide a way to make this application secure. 
- Probably pass tokens to be authenticated.
- Add spring-security and access roles for endpoints.
- API application should be behind an API gateway (apigee or axway).
- Use spring-cloud for sensitive data eg. database credentials

2. Maintainability 
- Introduce versioning system as early as possible.

3. Reliability 
- Data integrity is not there, there is no check regarding if name or email is existing. 

4. Scalability 
- Introduce proper DB (either RDBMS or NoSQL depending on its usage)
- Introduce proper caching mechanism either Redis, well just use redis.

5. Monitoring and Observability 
- Add logging mechanism such as log4j or logback.
- Add spring-sleuth for much easier logging aggregation.
- Already added actuator, this is a simple way so we can integrate it to load balancers.

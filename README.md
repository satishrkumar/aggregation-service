# aggregation-service

[![Build Status](https://travis-ci.org/codecentric/springboot-sample-app.svg?branch=master)](https://travis-ci.org/codecentric/springboot-sample-app)
[![Coverage Status](https://coveralls.io/repos/github/codecentric/springboot-sample-app/badge.svg?branch=master)](https://coveralls.io/github/codecentric/springboot-sample-app?branch=master)
[![License](http://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

Minimal [Spring Boot](http://projects.spring.io/spring-boot/) sample app.

## Requirements

For building and running the application you need:

- [JDK 11](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)
- [Docker](https://docs.docker.com/get-docker/)
- docker container run --publish 4000:4000 qwkz/backend-services:latest.

## How to Run 

This application is packaged as a war which has Tomcat 9.0.x embedded. No Tomcat or JBoss installation is necessary. You run it using the ```java -jar``` command.


* You can build the project and run the tests by running ```mvn clean install```
* Once successfully built, you can run the service by following methods:
```
    mvn spring-boot:run
```
* Check the stdout to make sure no exceptions are thrown

Once the application runs you should see something like this

```
    2023-03-16 20:24:07.432  INFO 30497 --- [           main] o.s.s.concurrent.ThreadPoolTaskExecutor  : 
    Initializing ExecutorService 'applicationTaskExecutor'
    2023-03-16 20:24:07.549  INFO 30497 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : 
    Tomcat started on port(s): 8080 (http) with context path ''
    2023-03-16 20:24:07.555  INFO 30497 --- [           main] c.a.a.AggregationServiceApplication      : 
    Started AggregationServiceApplication in 1.167 seconds (JVM running for 1.388)
    
```

* Build Docker image:<br>
    * docker build -t aggregation-service .

* Docker command to run on local machine:



Use four spaces to indent content between bullet points

1. docker network rm test<br>
1. docker network create test<br>
1. docker stop satishContainer satishContainer2<br>
1. docker rm satishContainer satishContainer2<br>
1. docker run -d -p 4000:4000  --network test --name satishContainer qwkz/backend-services<br>
1. docker run -d -p 8080:8080 -e base-url=http://satishContainer:4000 --network test --name satishContainer2 aggregation-service<br>

* Curl command to test api:

```
 curl --location 'http://127.0.0.1:8080/aggregation?shipmentsOrderNumbers=987654321%2C123456789&trackOrderNumbers=987654321%2C123456789&pricingCountryCodes=NL%2CCN'
``` 
Response
{
    "shipments": {
        "987654321": [
            "ENVELOPE",
            "PALLET",
            "ENVELOPE"
        ],
        "123456789": [
            "ENVELOPE",
            "PALLET"
        ]
    },
    "track": {
        "987654321": "IN_TRANSIT",
        "123456789": "COLLECTED"
    },
    "pricing": {
        "NL": 68.38777578114804,
        "CN": 38.4670090117577
    }
}
```
 curl --location 'http://127.0.0.1:8080/aggregation?shipmen
* Design:   

![Image name](./AggregationController_aggregate.svg)

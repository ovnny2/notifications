# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.7.5/gradle-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.7.5/gradle-plugin/reference/html/#build-image)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.7.5/reference/htmlsingle/#using.devtools)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.7.5/reference/htmlsingle/#web)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.7.5/reference/htmlsingle/#data.sql.jpa-and-spring-data)
* [Spring Data MongoDB](https://docs.spring.io/spring-boot/docs/2.7.5/reference/htmlsingle/#data.nosql.mongodb)
* [Validation](https://docs.spring.io/spring-boot/docs/2.7.5/reference/htmlsingle/#io.validation)
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/2.7.5/reference/htmlsingle/#actuator)
* [Prometheus](https://docs.spring.io/spring-boot/docs/2.7.5/reference/htmlsingle/#actuator.metrics.export.prometheus)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Accessing Data with MongoDB](https://spring.io/guides/gs/accessing-data-mongodb/)
* [Validation](https://spring.io/guides/gs/validating-form-input/)
* [Building a RESTful Web Service with Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)

### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)



## Environments Mongodb

        # host ports
        SERVER_HOST_PORT=8080
        MONGO_HOST_PORT=27017
        
        # host paths to mount
        MONGO_DATA_HOST_PATH="/home/ovny/Desktop/projects/pessoais/spring-apis/environments/mongodb/data"
        MONGO_LOG_HOST_PATH="/home/ovny/Desktop/projects/pessoais/spring-apis/environments/mongodb/log"
        MONGO_INITDB_SCRIPTS_HOST_PATH="/home/ovny/Desktop/projects/pessoais/spring-apis/environments/mongodb/.initdb.d"
        
        # application
        APP_NAME=app_notification
        NETWORK_NAME=ovnny_network-001
        
        # mongodb
        MONGO_AUTO_INDEX_CREATION=true
        MONGO_ROOT_USERNAME=admin
        MONGO_ROOT_PASSWORD=pass
        MONGO_DB_USERNAME=ovnny
        MONGO_DB_PASSWORD=pass
        MONGO_DB=mongo_notification

## initdb.d file - [ Mongodb ]

        #!/bin/bash
        mongo -u "$MONGO_INITDB_ROOT_USERNAME" -p "$MONGO_INITDB_ROOT_PASSWORD" --authenticationDatabase "$rootAuthDatabase" "admin" --eval "db.createUser({ user: '$MONGO_DB_USERNAME', pwd: '$MONGO_DB_PASSWORD', roles: [{ role: 'dbOwner', db: '$MONGO_INITDB_DATABASE' }] })"


    spring.data.mongodb.uri=mongodb://{MONGO_DB_USERNAME}:{MONGO_DB_PASSWORD}@localhost:27017/{MONGO_DB}?authSource={MONGO_INITDB_DATABASE}&readPreference=primary&appname={APP_NAME}&ssl=false
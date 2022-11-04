# notifications
Dedicated to analysis and deliberated studies of Java's ecosystem, microservices and software engineering best practices


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
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
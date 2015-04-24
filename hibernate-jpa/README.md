# Hibernate JPA

**Dependencies:**
- maven
- java 8

## Packages details
- **application**: contains the JAX-RS application implementation and restful api documentation;
- **resource**: contains JAX-RS resources used by the application;
- **repository**: contains classes which communicates with the persistence store; 
- **service**: contains classes which controls business logic and links the resource to the repository layer;
- **dto**: contains classes which are transferred from resources to services layers;
- **entity**: contains classes which maps to the database schema. They are also being transferred from the service to the repository layer;
- **mapper**: contains classes which maps dtos to entities.

## Api documentation
Swagger is being used to generate the api documentation in json format. You can find the documentation at: http://localhost:8080/api/swagger.json.

## building
```shell
$ ./build
```

## start the application
```shell
$ ./run
```

## TODO
- add unit tests
- add integration tests
- update javadoc for all classes

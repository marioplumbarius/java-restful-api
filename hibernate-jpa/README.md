# Hibernate JPA

**Dependencies:**
- maven
- jave 8

## Classes structure
- **resource**: class which responds to http requests
- **domain (dto / entity)**: class which is transferred through the application and maps the database table schema
- **repository (dao)**: class which access database
- **service**: class which acts as an abstraction layer between the controller and the persistence layers

## Api documentation
Swagger is being used to generate the api documentation in json format. You can find the documentation at: http://localhost:8080/api/swagger.json.

## building
```shell
$ JAVA_HOME=$JAVA_HOME_8 ./build
```

## start the application
```shell
$ JAVA_HOME=$JAVA_HOME_8 ./run
```

## TODO
- add unit tests
- add integration tests
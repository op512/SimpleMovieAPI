# PREREQUISITES
- java 11
- SQL Server 2016 or later 
- Run setupdatabase.sql to create MoviesDatabase and set up Logins and Users.

# Running App
- Run "mvn clean install" to build with tests.
- Deploy using "mvn  spring-boot:run"
- API endpoints can be viewed at http://localhost:8087/swagger-ui/index.html
## Auth
x-api-key header must be included in requests with value "userkey" allowing access to view movies and "adminkey" allowing additional access to create and modify movies.
## Assumptions
- Proper security solution was not expected
- Sql server available to use
- A movie can only have 1 director

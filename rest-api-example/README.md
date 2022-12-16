# Spring Boot Demo

## Overview

This is the repository for all the technologies covered for Basic Bootcamp training. Check out the following training documents on how to build the project step by step.

* [OpenAPI Specification (OAS) and Swagger Tools Guide](https://bookstack.ms3-inc.com/books/ms3-basecamp/page/openapi-specification-%28oas%29-and-swagger-tools-guide)
* [Create a REST API using Spring Boot](https://bookstack.ms3-inc.com/books/ms3-basecamp/page/create-a-rest-api-using-spring-boot)
* [Add Unit Test on Spring Boot REST API](https://bookstack.ms3-inc.com/books/ms3-basecamp/page/add-unit-test-on-spring-boot-rest-api)

### OpenAPI Specification (OAS)

The hands-on training starts with designing the API using OAS and Swagger editor tools. We will learn how to create the API specification which will serve as the API contract and design document. The completed OAS is located [here](https://bitbucket.org/mountainstatesoftware/spring-boot-training/src/develop/oas/).

### Spring Boot

The Spring Boot demo application is a working RESTful API that follows the OAS. This application demonstrates the following:

* How to make a REST API using Spring Boot
* Spring components and annotations
* Use of Spring profiles
* Controller and Service layer unit testing
* Authentication using Keycloak

The complete code is located [here](https://bitbucket.org/mountainstatesoftware/spring-boot-training/src/develop/src/).

## Technologies Used

* OpenAPI Specification (OAS)
* Java 8+
* Apache Maven
* JUnit
* Postman
* JMeter
* Keycloak
* Docker
* Jenkins

## How to Run the Spring Boot Project Locally

### Using Eclipse,

* Open Git Perspective and clone this repository.
* Right-click on the cloned repository and import as a Maven project.
* Once Maven is done downloading the dependencies, go to Run Configurations.
* Create a New Configuration under Spring Boot App.
* Provide a name.
* Select the `spring-boot-demo` project.
* Search for the main class `com.example.SpringBootDemoApplication`.
* Switch to Arguments tab and the following VM arguments:

```
-Dspring.profiles.active=local
-Dspring.config.name=spring-boot-demo
```

* Click Apply and Run.

### Using CLI,

* Git clone this repository.
* Go to project directory.
* Run: 

```
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.config.name=spring-boot-demo --spring.profiles.active=local"
```

### Using a Profile other than `local`

If you run the API using the `dev` or `prod` profile, it will require a Keycloak Authorization Server (AS). A Keycloak AS should be running prior to running the API. For information on how to run and set up Keycloak, check the **Authentication using Keycloak** section below.

## API Testing

### Unit Testing

This demo contains sample Controller and Service tests using JUnit, MockMvc and Mockito. Not all API operations are tested. Trainees will have to figure this out on their own.

In the `EmployeeControllerTest`, MockMvc is used to call the API endpoints and perform the assertions. MockBean is used to simulate the service object.

* GET `/employees` with result
* GET `/employees` with no result
* GET `/employees/{id}` is found
* GET `/employees/{id}` is not found
* POST `/employees` is successful
* DELETE `/employees/{id}` is found
* DELETE `/employees/{id}` is not found

In `EmployeeServiceTest`, MockBean is used to simulate the repository.

* `getEmployeesHasResult()`
* `getEmployeeByIdFound()`
* `getEmployeeByIdNotFound()`
* `saveEmployee()`
* `deleteEmployeeById()`

### Integration Testing

Postman is used to test the individual requests and to run the provided collection inside the `postman` folder.

To run the collection in Postman,

* Open Postman and click Import.
* Select the collection and environment files: `basecamp-demo.postman_collection.json` and `basecamp-demo-local.postman_environment.json`
* Once the collection and environment is loaded, click Runner button.
* Select the `basecamp-demo` collection, choose `basecamp-demo-local` environment and click Start Run.

> Note: The `basecamp-demo.postman_collection-v2.json` file contains pre-request script to call Keycloak Auth Server and save the access token in a collection variable.

Another way to run the collection is by using Newman. Newman is a CLI command that can run Postman collections. This tool is important as it will be used in the Jenkins pipeline.

To install Newman, follow the steps in the [official documentation](https://www.npmjs.com/package/newman).

It is also recommended to install the HTML reporter for Newman to have the test results display in a presentable HTML format. Install the HTML reporter via:

```
npm install -g newman-reporter-html
```

To run the provided Postman collection, run the following:

```
newman run postman/basecamp-demo.postman_collection.json -e postman/basecamp-demo-local.postman_environment.json -r cli,html --reporter-html-export target/site/newman/tests-report.html
```

Resulting report can be viewed in the CLI or by going to `target/site/newman/` and opening the `test-report.html` file in a web browser.

### Load Testing

JMeter is used to test the performance of the API. Download JMeter in the [official site](https://jmeter.apache.org/).

Once JMeter is downloaded and extracted,
* Run the GUI application by running `/jmeter-folder/bin/jmeter.sh` or `jmeter.bat` for Windows.
* Open the provided JMeter file in the project under the `jmeter` folder. You can adjust the number of threads running.
* Click Run.
* Once JMeter is done running the test, click on each of the requests and view the result table.

Another way of running JMeter is by using CLI. This is how JMeter test will be done inside a Jenkins pipeline. To run via CLI:

```
./jmeter -n -t /path/to/project/spring-boot-demo/jmeter/basecamp-demo-test-plan.jmx -l  /path/to/project/spring-boot-demo/jmeter/result.jtl
```

## Authentication using Keycloak

### Installation and set up of Keycloak Server

Download the Keycloak standalone server - https://www.keycloak.org/downloads.html

Extract the downloaded zip file and navigate to `/keycloak-root/standalone/configuration/standalone.xml`. Update the http port from `8080` to something else like `9000` so that spring-boot-demo API can still use port `8080`.

```
FROM
<socket-binding name="http" port="${jboss.http.port:8080}"/>

TO
<socket-binding name="http" port="${jboss.http.port:9000}"/>
```

Run `./standalone.bat` or `./standalone.sh` to run the Keycloak server. Navigate to http://localhost:9000 and log in with admin account. First time log in will prompt admin user creation.

Once logged in, add a new realm and call it `demo`. Next steps are to create a Client, Role and User.

Click Clients --> Create. Name the client `spring-boot-demo` and click Save. On the next screen (Settings), enter the values below.

```
Client ID: spring-boot-demo
Client Protocol: openid-connect
Access Type: confidential
Valid Redirect URIs: http://localhost:8080/*

# expand the Authentication Flow Overrides
Browser Flow: direct grant
Direct Grant Flow: direct grant
```

Click the Credentials tab and get the Secret value.

Click Roles --> Add Role. Enter `user` for Role Name. Click Save.

Click Users --> Add User. Enter `demouser` for Username. Click Save. On the next screen, click Credentials tab, set `password` for password and turn off Temporary toggle. Click Role Mappings, add `user` to Assigned Roles.

The Keycloak client is now set up and its configuration can be accessed via `http://127.0.0.1:9000/auth/realms/demo/.well-known/openid-configuration`.

Run the configuration URL above using a GET request. This will return a response containing the Token Endpoint that we will use to generate a a JWT token. Response will look like:

```
{
    "issuer": "http://127.0.0.1:9000/auth/realms/demo",
    "authorization_endpoint": "http://127.0.0.1:9000/auth/realms/demo/protocol/openid-connect/auth",
    "token_endpoint": "http://127.0.0.1:9000/auth/realms/demo/protocol/openid-connect/token",
    ...
}
```

Get the `token_endpoint` and either use curl or Postman to send a POST request to this URL with the following encoded form body:

```
curl -L -X POST 'http://localhost:9000/auth/realms/demo/protocol/openid-connect/token' \
-H 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'client_id=spring-boot-demo' \
--data-urlencode 'grant_type=password' \
--data-urlencode 'client_secret=bcb96074-ee7f-45b5-b43d-0b89c6ad9885' \
--data-urlencode 'scope=openid' \
--data-urlencode 'username=demouser' \
--data-urlencode 'password=password'
```

This will give a response body containing the `access_token` that will be used in the `Authorization` header property of the spring-boot-demo API.

```
{
    "access_token": "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICI3c3N1ZzZNTFY2eUhUV3BLZlhNZWpYTklUem9DZVlWb3dLbUYyZUlFRkRnIn0.eyJleHAiOjE2MDMwNTQ4NjIsImlhdCI6MTYwMzA1NDU2MiwianRpIjoiMjRkOGRmOGUtOTBhZS00Zjg3LTkwMzktZGFkNjczMjE4NjU2IiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo5MDAwL2F1dGgvcmVhbG1zL2RlbW8iLCJhdWQiOiJhY2NvdW50Iiwic3ViIjoiZDRmNjc4YTctOTJlNi00M2YyLWIyNDAtM2IxNzExYTVlYjM4IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoic3ByaW5nLWJvb3QtZGVtbyIsInNlc3Npb25fc3RhdGUiOiIwZDdlMjgyMC0wZjFjLTQxZGEtOGU1NC1mOGY3YjEyNTYyZTMiLCJhY3IiOiIxIiwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm9mZmxpbmVfYWNjZXNzIiwidW1hX2F1dGhvcml6YXRpb24iLCJ1c2VyIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJlbWFpbCBwcm9maWxlIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJkZW1vdXNlciJ9.TBrLILb3JNRM_Yhz3unXIUnEusZ1UMKychy7QIB2IqAzKC-1vklNW3fKHDwXso6omERShJgsyiPALXd2UMrpo54JWGr4-eIMpBhLJJHt7WGSV1znhP4XCPUx_ZtZ55foeuC6Cb-0i9ZeInCozqCs-oD8Hulcok09WOzZK13fayYp3JN8yxOFfwYH2xlp2E-zwm0cj8Mu6UT-lvc3lFuU3Zhccz9OlYdoHHG6XsZtpfb2XUPM6AmSGFz-QXeysEzytlucJdYprpiXthwvBhm0pRMhLJ9vO01pi3lbS9FYCLD_wLWbGe-IBVjmPYR48esQreDxYLvVZ6jjawLh5OBOWw",
    "expires_in": 300,
    "refresh_expires_in": 1800,
    "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICIyNGNkMGVlMy1jNDk1LTQxMzktYTJkZS0xMzY5OGQ1Njg3NzgifQ.eyJleHAiOjE2MDMwNTYzNjIsImlhdCI6MTYwMzA1NDU2MiwianRpIjoiMmUwNTA3MGEtNTFiZi00NTYwLTkxN2ItYWQ5ZmNjYTk3NWVlIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo5MDAwL2F1dGgvcmVhbG1zL2RlbW8iLCJhdWQiOiJodHRwOi8vbG9jYWxob3N0OjkwMDAvYXV0aC9yZWFsbXMvZGVtbyIsInN1YiI6ImQ0ZjY3OGE3LTkyZTYtNDNmMi1iMjQwLTNiMTcxMWE1ZWIzOCIsInR5cCI6IlJlZnJlc2giLCJhenAiOiJzcHJpbmctYm9vdC1kZW1vIiwic2Vzc2lvbl9zdGF0ZSI6IjBkN2UyODIwLTBmMWMtNDFkYS04ZTU0LWY4ZjdiMTI1NjJlMyIsInNjb3BlIjoiZW1haWwgcHJvZmlsZSJ9.StHfud88tOtiA1fXX5yszDcGBhor70QjSp4eRBHbK2w",
    "token_type": "bearer",
    "not-before-policy": 0,
    "session_state": "0d7e2820-0f1c-41da-8e54-f8f7b12562e3",
    "scope": "email profile"
}
```

### Testing using spring-boot-demo API

In the `application.properties`, make sure the following Keycloak configurations are present. The values should match with the Keycloak setup:

```
keycloak.auth-server-url=http://localhost:9000/auth
keycloak.realm=demo													# keycloak realm
keycloak.resource=spring-boot-demo									# keycloak client ID
keycloak.public-client=true
keycloak.security-constraints[0].authRoles[0]=user			# this is the keycloak role and not `demouser` user
keycloak.security-constraints[0].securityCollections[0].patterns=/*, /employees/*
```

In Postman, add a GET `http://localhost:8080/employees` request and add `Authorization` header property with a value `Bearer <paste access_token value here>`.


### Keycloak Export and Import Realm

```
# Export realm, roles and users from a running Keycloak server

docker exec -it spring-boot-demo_keycloak_1 /opt/jboss/keycloak/bin/standalone.sh \
-Djboss.socket.binding.port-offset=100 -Dkeycloak.migration.action=export \
-Dkeycloak.migration.provider=singleFile \
-Dkeycloak.migration.realmName=demo \
-Dkeycloak.migration.usersExportStrategy=REALM_FILE \
-Dkeycloak.migration.file=/tmp/demo.json

# Run a new Keycloak instance in Docker and import a realm JSON file

docker run --rm  \
   --name test-keycloak \
   -e KEYCLOAK_USER=admin \
   -e KEYCLOAK_PASSWORD=admin \
   -e KEYCLOAK_IMPORT=/tmp/demo.json  -v /tmp/demo.json:/tmp/demo.json \
   -p 8180:8180 \
   -it jboss/keycloak:latest \
   -b 0.0.0.0 \
   -Djboss.http.port=8180 \
   -Dkeycloak.profile.feature.upload_scripts=enabled
```

## CI/CD using Jenkins

Two Jenkins files are included in this demo and can be found inside the `ci` folder.

* `Jenkinsfile-local` requies a local Jenkins setup. Use this to test different stages of a build pipeline locally.
* `Jenkinsfile-ms3` can be used in the MS3 Dev Jenkins instance (https://dev-jenkins.sso.kube.cloudapps.ms3-inc.com/). It contains a complete pipeline from unit testing to publishing in MS3 Docker Registry.

To use MS3 Jenkins, follow the documentation in this [Bookstack page](https://bookstack.ms3-inc.com/books/ms3-basecamp/page/pipeline-example).

## Docker

This demo project can also run inside a Docker container. Several files are included for demonstration:

* `Dockerfile` contains the script to build the API's image.
* `keycloak/Dockerfile` contains the the script to build a Keycloak container using `jboss/keycloak` image and configuring it with the provided `demo.json` realm file.
* `docker-compose-local-build.yml` contains the manifest to build the API and Keycloak containers using their corresponding `Dockerfile`.
* `docker-compose-demo.yml` contains the manifest to pull the API image from MS3 Docker Registry and build the Keycloak container using Dockerfile.

To operate Docker Compose,

```
# build the containers
docker-compose -f .\docker-compose-demo.yml up --build

# start containers
docker-compose -f .\docker-compose-demo.yml start

# stop containers
docker-compose -f .\docker-compose-demo.yml stop

# remove containers
docker-compose -f .\docker-compose-demo.yml rm
```

## References

* https://www.postman.com/
* https://www.npmjs.com/package/newman
* https://jmeter.apache.org/
* https://www.keycloak.org/2017/05/easily-secure-your-spring-boot.html
* https://developers.redhat.com/blog/2020/01/29/api-login-and-jwt-token-generation-using-keycloak/
* https://bookstack.ms3-inc.com/books/ms3-basecamp/page/pipeline-example

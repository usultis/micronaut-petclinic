# Micronaut PetClinic Sample Application

This Petclinic is a [Micronaut](https://micronaut.io/) application which I forked from [Spring PetClinic](https://github.com/spring-projects/spring-petclinic) and tried to migrate from Spring to Micronaut to know how Micronaut works. I respect Spring so much, and I think I should replace Spring logos with Micronaut logos to avoid confusion.

## Running petclinic locally

### 1. Clone Project

```
git clone https://github.com/bufferings/micronaut-petclinic.git
cd micronaut-petclinic
```

### 2. Start PostgreSQL

This Petclinic uses PostgreSQL. You could start PostgreSQL with docker:

```
docker-compose up
```

### 3. Run Application

```
./mvnw compile exec:exec
```

### 4. Access PetClinic

You can then access petclinic here: http://localhost:8080/

### Or you can run it from JAR

```
./mvnw package
java -jar target/*.jar
```

## GraalVM Native Image

This PetClinic supports [GraalVM](https://www.graalvm.org/) native image build.

```
./mvnw package && docker build -t micronaut-petclinic .
```

I usually have ☕ (around 10 mins on my laptop) to wait for the build finishes.

```
# Docker for Mac or Windows
export HOST_NAME=host.docker.internal
# Linux
export HOST_NAME=172.17.0.1

docker run --rm -p 8080:8080 -e JDBC_URL=jdbc:postgresql://${HOST_NAME}:5432/petclinic micronaut-petclinic
```

It starts in 300-400ms.

# License

The Micronaut PetClinic sample application is released under version 2.0 of the [Apache License](https://www.apache.org/licenses/LICENSE-2.0).

# TODO

- [x] Basic features with Micronaut
- [x] Native Image
- [ ] Logging
- [ ] Validation
- [ ] Error Handling
- [ ] Cache
- [ ] Hot Reloading?
- [ ] Tests
- [ ] Management

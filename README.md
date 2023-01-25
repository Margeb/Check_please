[<ins>English</ins>](README.md) - [Polish](README.pl.md)

# Check, Please Application
Check, please - application that allows You to track all the expenses among your groups of friends.

## Table of Contents
* [General Information](#general-information)
* [Technologies Used](#technologies-used)
* [Prerequisites](#prerequisites)
* [Setup](#setup)
* [Author](#author)

## General Information

The idea behind creating this app is to track how much money each person spend when ordering something for the group (mostly food). Let's say someone ordered food, paid for everyone and instead of getting paid they can save the information how much that person did spend and what was the cost of each person's food. You can easily create new groups, add people to it and create new bills.
The application is based on the REST architecture, the Minimum Viable Product (MVP) and MVC (Model-View-Controller) architectural pattern.

## Technologies Used
### Development
- [Java 19](https://openjdk.org/projects/jdk/19/)
- [Spring Boot 3](https://spring.io/projects/spring-boot)
- [Spring Data](https://spring.io/projects/spring-data)
- [PostgreSQL (docker)](https://www.postgresql.org/)
- [Maven 3.x](https://maven.apache.org/)
- [Git](https://git-scm.com/)


### Test
- [JUnit5](https://junit.org/junit5/)
- [Mockito](https://site.mockito.org/)

## Prerequisites
The following tools are required to start the application:

- [IntelliJ IDEA](https://www.jetbrains.com/idea/),
- [Java 19](https://openjdk.org/projects/jdk/19/)
- [Maven 3.x](https://maven.apache.org/download.cgi),
- [Docker](https://docs.docker.com/get-docker/)

## Setup

To run this project, please clone this repository and create a local copy on your computer.

After downloading project configure your database and db server in few step:

- Create database connection with Docker pasting this into command line:

docker run --name postgresCheck -e POSTGRES_PASSWORD=password -d -p 5432:5432 postgres

- Connect with server:

Login: postgres

Password: password

- Create database in server:

create database check_please;


## Author
Created by Margeb.

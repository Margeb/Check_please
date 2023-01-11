[Angielski](README.md) - [<ins>Polski</ins>](README.pl.md)

# Aplikacja Check, please

Check, please - aplikacja, która pozwala na śledzenie wydatków w grupie przyjaciół.
## Spis treści

* [Informacje ogólne](#informacje-ogólne)
* [Zastosowane technologie](#zastosowane-technologie)
* [Wymagane aplikacje/narzędzia](#wymagane-aplikacjenarzędzia)
* [Ustawienia](#ustawienia)
* [Autor](#autor)

## Informacje ogólne

Głównym założeniem aplikacji jest śledzenie ile każda osoba wydaje pieniędzy zamawiając coś (głównie jedzenie) dla grupy znajomych. Załóżmy, że ktoś zamówił jedzenie, zapłacił za wszystkich i zamiast od razu dostać pieniądze to możemy zapisać ile dana osoba zapłaciła i ile kosztowało jedzenie każdej osoby. Możemy łatwo tworzyć nowe grupy, dodawać do nich osoby oraz tworzyć nowe rachunki.
Aplikacja jest oparta na architekturze REST i modelu Minimum Viable Product (MVP).

## Zastosowane technologie

### Projektowanie

- [Java 19](https://openjdk.org/projects/jdk/19/)
- [Spring Boot 3](https://spring.io/projects/spring-boot)
- [Spring Data](https://spring.io/projects/spring-data)
- [PostgreSQL (docker)](https://www.postgresql.org/)
- [Maven 3.x](https://maven.apache.org/)
- [Git](https://git-scm.com/)

### Testy

- [JUnit5](https://junit.org/junit5/)
- [Mockito](https://site.mockito.org/)

## Wymagane aplikacje/narzędzia

Do uruchomienia aplikacji wymagana jest instalacja następujących narzędzi:

- [IntelliJ IDEA](https://www.jetbrains.com/idea/),
- [Java 19](https://openjdk.org/projects/jdk/19/)
- [Maven 3.x](https://maven.apache.org/download.cgi),
- [Docker](https://docs.docker.com/get-docker/)

## Ustawienia

W celu uruchomienia projektu, sklonuj to repozytorium i utwórz lokalną kopię na swoim komputerze.

Po pobraniu projektu skonfiguruj swoją bazę daych i serwer db w kilku krokach:

- Utwórz połączenie z bazą danych za pomocą Dockera wklejając w linii poleceń:

docker run --name postgresCheck -e POSTGRES_PASSWORD=password -d -p 5432:5432 postgres

- Połącz się z serwerem:

Login: postgres

Password: password

- Utwórz bazę danych:

create database check_please;

## Autor

Margeb
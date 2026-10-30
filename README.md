# Poseidon Capital Solutions

[![CI](https://github.com/Hyudoro/poseidon-capital-solutions/actions/workflows/ci.yml/badge.svg)](https://github.com/Hyudoro/poseidon-capital-solutions/actions/workflows/ci.yml)

Spring Boot web application for a fictional trading desk: curve points, bid
lists, ratings, rule names and trades, behind role-based access (user / admin).

## Stack

- Java 17, Spring Boot 3.1.0, Thymeleaf, Bootstrap 4
- MySQL 8
- Maven 3.9 (via the multi-stage Docker build)
- Docker & Docker Compose

## Run it

Create a `.env` file next to `compose.yaml`:

```env
DB_USER=demo
DB_PASSWORD=change-me
```

Then build and start:

```sh
docker compose up --build
```

The app listens on http://localhost:8080. A demo admin account is created on
first start: **admin / test**.

The Dockerfile builds in stages: the test stage runs `mvn verify` before the
jar is packaged, and the JaCoCo report can be exported on demand:

```sh
docker build --target report --output type=local,dest=./jacoco-report .
```

## Endpoints

Access levels:
- public: no login needed
- logged in: any authenticated user
- admin only: must have ADMIN role

## Home
GET  /              public       home page
GET  /admin/home    admin only   redirects to /bidList/list

## Login
GET  /app/login                    public       login page
GET  /app/error                    public       403 error page
GET  /app/secure/article-details   admin only   list of users (via secure path)
POST /app-logout                   public       logs out, redirects to /app/login

## Bid List
GET  /bidList/list          logged in   list all bids
GET  /bidList/add           logged in   show add bid form
POST /bidList/validate      logged in   create a bid
GET  /bidList/update/{id}   logged in   show edit form for a bid
POST /bidList/update/{id}   logged in   update a bid
GET  /bidList/delete/{id}   logged in   delete a bid

## Curve Point
GET  /curvePoint/list          logged in   list all curve points
GET  /curvePoint/add           logged in   show add curve point form
POST /curvePoint/validate      logged in   create a curve point
GET  /curvePoint/update/{id}   logged in   show edit form for a curve point
POST /curvePoint/update/{id}   logged in   update a curve point
GET  /curvePoint/delete/{id}   logged in   delete a curve point

## Rating
GET  /rating/list          logged in   list all ratings
GET  /rating/add           logged in   show add rating form
POST /rating/validate      logged in   create a rating
GET  /rating/update/{id}   logged in   show edit form for a rating
POST /rating/update/{id}   logged in   update a rating
GET  /rating/delete/{id}   logged in   delete a rating

## Rule Name
GET  /ruleName/list          logged in   list all rules
GET  /ruleName/add           logged in   show add rule form
POST /ruleName/validate      logged in   create a rule
GET  /ruleName/update/{id}   logged in   show edit form for a rule
POST /ruleName/update/{id}   logged in   update a rule
GET  /ruleName/delete/{id}   logged in   delete a rule

## Trade
GET  /trade/list          logged in   list all trades
GET  /trade/add           logged in   show add trade form
POST /trade/validate      logged in   create a trade
GET  /trade/update/{id}   logged in   show edit form for a trade
POST /trade/update/{id}   logged in   update a trade
GET  /trade/delete/{id}   logged in   delete a trade

## User
GET  /user/list          admin only   list all users
GET  /user/add           admin only   show add user form
POST /user/validate      admin only   create a user
GET  /user/update/{id}   admin only   show edit form for a user
POST /user/update/{id}   admin only   update a user
GET  /user/delete/{id}   admin only   delete a user

## Tests

The suite covers all six domains, controller slice tests with MockMvc,
service unit tests with Mockito, and the domain, security and password
tests: 109 tests in all. They run on an in-memory H2 database, so building
needs no MySQL. The Docker image's test stage runs the same suite before
the jar is packaged, and CI repeats it on every push and pull request to
main.

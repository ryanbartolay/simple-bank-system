# Simple Bank System

## Requirements

- Java 8 or later
- Maven

## Setup

1. Run Project under *simple-bank-system-web* module.

        cd simple-bank-system/simple-bank-system-web
        mvn spring-boot:run

    Running the application includes:
    - Creation of the database *bank*
    - Creation of tables *account, transaction, user* and columns 
    - Initialization of records
    - This will run the project as standalone

2. Open browser to **localhost:9012**

## Branches

There are 2 branches

- **main** - using spring mvc + consuming of rest endpoints
- **feature/spring_mvc** -  using spring mvc only

## Override default configuration

You can override default configuration via command line parameters

        mvn spring-boot:run -Ddb.user=root -Ddb.password=ryan1234 -Ddb.host=localhost -Ddb.port=3007

You can find the application default settings under **simple-bank-system-web/src/main/resources/application.yml**

For you reference below are the default configuration and can be *overridden via command line*:

**application.yml**

        server:
          port: 9012
        db:
          user: root
          password: ryan1234
          host: localhost
          port: 3307
          name: bank
        bank:
          currency: USD
          withdraw:
            limit: 800
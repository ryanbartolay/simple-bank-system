# Simple Bank System

## Requirements

- Java 8 or later
- Maven

## Setup

1. Build Project (from root directory)

        mvn clean install package

2. Run Project under *simple-bank-system-web* module.

        cd simple-bank-system-web
        mvn spring-boot:run

    > This will run the project as standalone,
    > Table columns and Initial data will be installed.

3. Open browser to **localhost:9012**

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
        bank:
          currency: USD
          withdraw:
            limit: 800

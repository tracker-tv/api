# Installation

```shell
git clone git@github.com:tracker-tv/api.git
```

```shell
cp docker-compose.override.yml.dist docker-compose.override.yml
```

Copy file configuration if you run the application on your host machine.
```shell
cp src/main/resources/application.properties src/main/resources/application-local.properties
```

Then edit `application-local.properties` like this if you run the application on your host.
```shell
app.datasource.ro.jdbc-url=jdbc:postgresql://localhost:5432/tv-tracker
app.datasource.ro.username=test-user
app.datasource.ro.password=test-password
#
app.datasource.rw.jdbc-url=jdbc:postgresql://localhost:5432/tv-tracker
app.datasource.rw.username=test-user
app.datasource.rw.password=test-password
#
logging.level.Exposed=debug
```

You can do the same thing for the `application-test.properties` if you run the test on your host.
```shell
cp src/test/resources/application-test.properties src/test/resources/application-test-local.properties
```

```shell
app.datasource.ro.jdbc-url=jdbc:postgresql://localhost:5433/tv-tracker-test
app.datasource.ro.username=test-user
app.datasource.ro.password=test-password
#
app.datasource.rw.jdbc-url=jdbc:postgresql://localhost:5433/tv-tracker-test
app.datasource.rw.username=test-user
app.datasource.rw.password=test-password

```

Run the migrations (it will up the database service)
```shell
docker compose up db-migration -d
```

## Test
```shell
docker compose run --rm test
```

## Run the app without cloning the repo

You can try the app if you have docker installed by following these steps:

### Create a network

```shell
docker network create tv_tracker
```

### Run a database

```shell
docker run --rm \
 -e POSTGRES_PASSWORD=test-password \
 -e POSTGRES_USER=test-user \
 -e POSTGRES_DB=tv-tracker \
 --name=tv_tracker_database \
 -p 5432:5432 \
 --network=tv_tracker \
 postgres:16.1
```

### Run migrations

```shell
docker run --rm \
 -e LIQUIBASE_LOG_LEVEL=FINE \
 -e LIQUIBASE_COMMAND_CHANGELOG_FILE=index.xml \
 -e LIQUIBASE_COMMAND_URL="jdbc:postgresql://localhost:5432/tv-tracker" \
 -e LIQUIBASE_COMMAND_USERNAME=test-user \
 -e LIQUIBASE_COMMAND_PASSWORD=test-password \
 -e LIQUIBASE_DATABASE_CHANGELOG_TABLE_NAME=tv_tracker_migrations \
 -e LIQUIBASE_DATABASE_CHANGELOG_LOCK_TABLE_NAME=tv_tracker_migrations_lock \
 --network=host \
 ghcr.io/tracker-tv/api/db-migration:2023-11-28_main_8f019f5 \
 update
```

### Run application

```shell
docker run --rm \
 -p 8080:8080 \
 -e APP_DATASOURCE_RO_JDBC_URL="jdbc:postgresql://tv_tracker_database:5432/tv-tracker" \
 -e APP_DATASOURCE_RO_USERNAME=test-user \
 -e APP_DATASOURCE_RO_PASSWORD=test-password \
 -e APP_DATASOURCE_RW_JDBC_URL="jdbc:postgresql://tv_tracker_database:5432/tv-tracker" \
 -e APP_DATASOURCE_RW_USERNAME=test-user \
 -e APP_DATASOURCE_RW_PASSWORD=test-password \
 --network=tv_tracker \
 ghcr.io/tracker-tv/api:2023-11-28_main_8f019f5
```

### Add data with a POST request

```shell
curl -X POST \
 -H "Content-Type: application/json" \
 -d '{"name": "Bryan Cranston"}' \
 localhost:8080/v1/actors
```

You can check the result with a GET endpoint:

```shell
curl -s \
 -H "Accept: application/json" \
 localhost:8080/v1/actors
```

## Conference Manager
- a learning project for spring-boot

## Database actions

- launch a postresql instance in docker

```bash
$ export HOST_PATH=`pwd`
$ docker-compose up
```

- execute necessary migrations over the conference_app db
```
$ docker exec -it conference-manager_postgres-demo_1 bash
$ psql -U postgres
$ create database conference_app;
$ \c conference_app;
$ psql -d conference_app \i /home/create_tables.sql
$ psql -d conference_app \i /home/insert_data.sql
$ \dt
$ \quit
```

- run the application in development mode using
```bash
Dspring.profiles.active=dev
```

- for intellij configuration set the VM option
```bash
-Dspring.profiles.active=dev
```

- view **Springboot Conference App.postman_collection.json** in your Postman app to explore the api.
#BeDemo-2

# prod database using psql



# building

## frontend build

```bash
mvn clean install
```
builds and test backend and builds frontend

or

```bash
cd src/main/webapp/
ng build --prod
```
# frontend development

```bash
npm install
ng serve
```

see [angular README](src/main/web/README.md)

## development backend

```bash
mvn spring-boot:run
```

# build backend

```bash
mvn clean install
```
## run 'prod'

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

## create db

```bash
psql
```


```sql
create database simplerest2;
```
```sql
create user myuser with encrypted password 'mypass';
```

```sql
grant all privileges on database simplerest2 to myuser;
```

```sql
\q
```

# deploying (no longer maintained)

```bash
heroku login
heroku create
git push heroku master
heroku open
```

#docker

```bash
mvn clean install
docker build -t amsdams/simplerest-prod .
docker-compose up
```

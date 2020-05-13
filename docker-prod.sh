docker build -t amsdams/simplerest-prod .
docker run -e "SPRING_PROFILES_ACTIVE=prod" -p 8080:8080 amsdams/simplerest-prod

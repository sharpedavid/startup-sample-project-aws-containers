FROM maven:3.6.0-jdk-11-slim AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package

FROM amazoncorretto:11-alpine-jdk as server
COPY --from=build /usr/src/app/target/*.jar app.jar
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} app.jar

FROM server as test
RUN echo "Much test"

FROM server as runtime
EXPOSE 80
ENTRYPOINT ["java","-jar","/app.jar"]

## Client
#FROM node:12-alpine AS client
#
## Install build deps
#RUN apk add python3 make g++
#
## Build client
#ENV REACT_APP_API_URL=""
#WORKDIR /client
#COPY client/package*.json ./
#RUN npm set progress=false && npm ci --no-cache
#COPY client/. .
#RUN npm run build
#
## Server
#FROM node:12-alpine AS server
#
## Run server
#COPY --from=client /client/build /client/build/.
#WORKDIR /server
#
#COPY server/package*.json ./
#RUN npm set progress=false && npm ci --no-cache
#COPY server/. .
#
#FROM server as test
#RUN npm run test
#
#FROM server as runtime
#EXPOSE 80
#CMD [ "npm", "run", "start" ]
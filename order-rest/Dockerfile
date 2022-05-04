FROM maven:3.6.1-jdk-8 as maven_builder
WORKDIR /order-rest
ADD pom.xml .
ADD . /book-rest
RUN mvn clean install
FROM tomcat:8.5.43-jdk8
COPY --from=maven_builder /order-rest/target/order.war /usr/local/tomcat/webapps
COPY --from=maven_builder /order-rest/tomcat-users.xml /usr/local/tomcat/conf
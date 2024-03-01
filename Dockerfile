FROM openjdk:21
VOLUME /tmp
ADD target/*.jar wallet.jar
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /wallet.jar" ]
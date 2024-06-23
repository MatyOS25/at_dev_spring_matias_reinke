# Use a imagem base do OpenJDK
FROM openjdk:17-jdk-slim

# Adiciona um argumento para especificar a versão do JAR
ARG JAR_FILE=target/*.jar

# Copia o JAR para o container
COPY ${JAR_FILE} app.jar

# Expõe a porta em que a aplicação irá rodar
EXPOSE 8080 9092 8082 81

# Comando para executar o JAR
ENTRYPOINT ["java", "-jar", "/app.jar"]


# Fase de Build - Maven
FROM maven:3.8.4-openjdk-17-slim AS build

# Copia o código-fonte e o arquivo pom.xml para o diretório de trabalho no contêiner
COPY pom.xml /app/
COPY src /app/src/

# Define o diretório de trabalho
WORKDIR /app

# Executa o build do projeto Maven sem realizar o download de dependências de testes
RUN mvn clean package -DskipTests

# Segunda fase do Dockerfile para criar a imagem final
FROM openjdk:17-jdk-slim

# Adiciona um argumento para especificar o arquivo JAR criado na fase de build anterior
ARG JAR_FILE=/app/target/*.jar

# Copia o JAR construído na fase anterior para o contêiner
COPY --from=build ${JAR_FILE} app.jar

# Expõe a porta em que a aplicação irá rodar (ajuste conforme necessário)
EXPOSE 8080 9092 8082 81

# Comando para executar o JAR na inicialização do contêiner
ENTRYPOINT ["java", "-jar", "/app.jar"]

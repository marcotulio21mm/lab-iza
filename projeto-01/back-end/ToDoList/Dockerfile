# Definindo a imagem base como Ubuntu
FROM ubuntu:latest AS build

# Atualizando o ambiente
RUN apt-get update -y && apt-get upgrade -y

# Instalando o OpenJDK
RUN apt-get install -y openjdk-17-jdk

# Copiando o código fonte para o container
COPY . .

# Instalando o Maven
RUN apt-get install -y maven

# Solicitando a geração do arquivo JAR com o Maven
RUN mvn clean install

# Escolhendo a imagem que executará a aplicação
FROM openjdk:17-jdk-slim

# Expondo a porta 8080 para acesso externo
EXPOSE 8080

# Copiando o arquivo JAR para execução
COPY --from=build target/*.jar app.jar

# Executando o arquivo JAR
CMD ["java", "-jar", "/app/app.jar"]

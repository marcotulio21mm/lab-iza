# Definindo a imagem base como Ubuntu
FROM ubuntu:latest AS build

# Atualizando o ambiente
RUN apt-get update -y && apt-get upgrade -y

# Instalando o OpenJDK
RUN apt-get install -y openjdk-17-jdk

# Instalando o Maven
RUN apt-get install -y maven

# Copiando o código fonte para o container
COPY . .

ENV URL=
ENV PORT=
ENV POSTGRES_DB=
ENV POSTGRES_USER=
ENV POSTGRES_PASSWORD=

# Solicitando a geração do arquivo JAR com o Maven
RUN mvn clean install -e

# Escolhendo a imagem que executará a aplicação
FROM openjdk:17-jdk-slim

# Expondo a porta 8080 para acesso externo
EXPOSE 8080

# Copy the .env file to the container (adjust the path as necessary)
#COPY --from=build .env .env

# Load environment variables from the .env file at runtime
#CMD export $(cat .env | xargs) && java -jar app.jar

# Copiando o arquivo JAR para execução
COPY --from=build target/*.jar app.jar

#CMD ["/bin/sh","-c","export $(cat .env | xargs) && java -jar app.jar"]
ENTRYPOINT ["java", "-jar", "app.jar"]

## Executando o arquivo JAR com as variáveis de ambiente carregadas
#CMD ["dotenv", "-e", ".env", "java", "-jar", "app.jar"]

# Definindo a imagem base como Ubuntu
FROM ubuntu:latest AS build

# Definindo variáveis de ambiente
ENV URL postgres://postgresroot:55AHPbYzwofEmC3XVcNkRydXhg39uRUz@dpg-coon71e3e1ms73b945v0-a/todolistdb_qwal
ENV PORT 5432
ENV POSTGRES_DB todolistdb_qwal
ENV POSTGRES_USER postgresroot
ENV POSTGRES_PASSWORD 55AHPbYzwofEmC3XVcNkRydXhg39uRUz

# Atualizando o ambiente
RUN apt-get update -y && apt-get upgrade -y

# Instalando o OpenJDK
RUN apt-get install -y openjdk-17-jdk

# Copiando o código fonte para o container
COPY . .

# Instalando o Maven
RUN apt-get install -y maven

# Solicitando a geração do arquivo JAR com o Maven
RUN mvn clean install -e

# Escolhendo a imagem que executará a aplicação
FROM openjdk:17-jdk-slim

# Expondo a porta 8080 para acesso externo
EXPOSE 8080

# Copiando o arquivo JAR para execução
COPY --from=build target/*.jar app.jar

# Executando o arquivo JAR
CMD ["java", "-jar", "/app/app.jar"]

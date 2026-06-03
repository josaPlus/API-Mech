# ══ STAGE 1 — Build con Ant + JDK 17 ════════════════════════
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

# Instalar Ant
RUN apt-get update && apt-get install -y ant && rm -rf /var/lib/apt/lists/*

# Copiar todo el proyecto
COPY . .

# Compilar con Ant
RUN ant -f build.xml dist

# ══ STAGE 2 — Runtime con Tomcat ═════════════════════════════
FROM tomcat:10.1-jdk17

RUN rm -rf /usr/local/tomcat/webapps/*

COPY --from=build /app/dist/MechAPIBueno.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
CMD ["catalina.sh", "run"]
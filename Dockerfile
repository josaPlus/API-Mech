# ══ STAGE 1 — Compilar con JDK 17 ════════════════════════════
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

# Copiar todo el proyecto
COPY . .

# Crear directorios necesarios
RUN mkdir -p build/WEB-INF/classes

# Compilar todos los archivos Java usando los JARs de WEB-INF/lib
RUN javac -d build/WEB-INF/classes \
    -cp "web/WEB-INF/lib/*" \
    $(find src/java -name "*.java")

# Copiar recursos web al directorio de build
RUN cp -r web/WEB-INF build/ && \
    cp -r web/META-INF build/ && \
    cp web/index.html build/ && \
    cp -r build/WEB-INF/classes build/WEB-INF/

# Empaquetar WAR
RUN cd build && jar -cf /app/MechAPIBueno.war .

# ══ STAGE 2 — Runtime con Tomcat ═════════════════════════════
FROM tomcat:10.1-jdk17

RUN rm -rf /usr/local/tomcat/webapps/*

COPY --from=build /app/MechAPIBueno.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
CMD ["catalina.sh", "run"]
# ══ STAGE 1 — Compilar con JDK 17 ════════════════════════════
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

# Copiar todo el proyecto
COPY . .

# Crear directorios de build
RUN mkdir -p build/WEB-INF/classes build/META-INF

# Compilar Java usando los JARs de WEB-INF/lib
RUN javac -d build/WEB-INF/classes \
    -cp "web/WEB-INF/lib/*" \
    $(find src/java -name "*.java")

# Copiar JARs al build
RUN cp -r web/WEB-INF/lib build/WEB-INF/

# Copiar web.xml
RUN cp web/WEB-INF/web.xml build/WEB-INF/

# Copiar META-INF y index.html
RUN cp web/META-INF/context.xml build/META-INF/
RUN cp web/index.html build/

# Empaquetar WAR
RUN jar -cf /app/MechAPIBueno.war -C build .

# ══ STAGE 2 — Runtime con Tomcat ═════════════════════════════
FROM tomcat:10.1-jdk17

RUN rm -rf /usr/local/tomcat/webapps/*

COPY --from=build /app/MechAPIBueno.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
CMD ["catalina.sh", "run"]
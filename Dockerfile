FROM icr.io/appcafe/open-liberty:full-java21-openj9-ubi-minimal
COPY src/main/liberty/config/server.xml /config/server.xml
COPY target/fieldPal-web.war /config/apps/
COPY target/liberty/wlp/usr/shared/resources/postgres/ /config/resources/postgres/
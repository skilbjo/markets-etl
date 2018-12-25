FROM quay.io/skilbjo/engineering:java-arm-latest

COPY target/uberjar/app.jar      /app.jar
COPY deploy                      /usr/local/deploy
COPY src                         /usr/local/src
COPY resources                   /usr/local/resources
COPY resources/backfill          /usr/local/backfill

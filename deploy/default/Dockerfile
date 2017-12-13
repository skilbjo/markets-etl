FROM gliderlabs/alpine:latest

ENV JAVA_HOME="/usr/lib/jvm/java-1.8-openjdk8" \
    JAVA_OPTS="-Duser.timezone=UTC -Xms256m -Xmx512m -XX:MaxMetaspaceSize=128m -Duser.timezone=PST8PDT"




COPY target/uberjar/app.jar      /app.jar
COPY deploy                      /usr/local/deploy
COPY resources                   /usr/local/resources
COPY src                         /usr/local/src
COPY deploy/default/bashrc       /root/.bashrc
COPY deploy/default/vimrc        /root/.vimrc

RUN apk add -U openjdk8 \
    bash bash-completion vim \
    tzdata coreutils && \
    cp /usr/share/zoneinfo/America/Los_Angeles /etc/localtime && \
    echo "America/Los_Angeles" >/etc/timezone

FROM gliderlabs/alpine:latest

ENV JAVA_HOME="/usr/lib/jvm/java-1.8-openjdk8" \
    JAVA_OPTS="-Duser.timezone=UTC -Xms256m -Xmx512m -XX:MaxMetaspaceSize=128m " \
    PATH="/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/bin:/usr/lib/jvm/java-1.8-openjdk/bin" \
    CMD="usr/local/deploy/bin/run-job"




COPY target/uberjar/app.jar      /app.jar
COPY deploy                      /usr/local/deploy
COPY src                         /usr/local/src
COPY deploy/default/bashrc       /root/.bashrc
COPY deploy/default/vimrc        /root/.vimrc

RUN apk add -U openjdk8 \
    bash bash-completion vim coreutils \
    tzdata && \
    cp /usr/share/zoneinfo/America/Los_Angeles /etc/localtime && \
    echo "America/Los_Angeles" >/etc/timezone

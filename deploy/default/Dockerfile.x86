FROM gliderlabs/alpine:latest

ENV JAVA_HOME /usr/lib/jvm/java-1.8-openjdk8

COPY __UBERJAR__                    /app.jar

RUN apk add -U openjdk8 \
    bash bash-completion curl vim \
    tzdata coreutils && \
    cp /usr/share/zoneinfo/America/Los_Angeles /etc/localtime && \
    echo "America/Los_Angeles" >/etc/timezone && \
    rm -rf /var/cache/apk/*

COPY deploy                         /usr/local/deploy
COPY src                            /usr/local/src
COPY deploy/default/bashrc          /root/.bashrc
COPY deploy/default/vimrc           /root/.vimrc

FROM resin/armhf-alpine:3.5

ENV JAVA_HOME /usr/lib/jvm/java-1.8-openjdk
ENV QEMU_EXECVE 1

COPY deploy/qemu/qemu-arm-static    /usr/bin/

COPY deploy                         /usr/local/deploy
COPY src                            /usr/local/src
COPY deploy/default/bashrc          /root/.bashrc
COPY deploy/default/vimrc           /root/.vimrc

COPY __UBERJAR__                    /app.jar

RUN ["qemu-arm-static","/sbin/apk","-U","add","openjdk8"]

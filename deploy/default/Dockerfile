FROM gliderlabs/alpine:latest

ENV CONSUL_PREFIX __CONSUL_PREFIX__
ENV CONSUL_SECRET_PREFIX __CONSUL_SECRET_PREFIX__

RUN apk update && \
    apk add postgresql-client && \
    apk add postfix mutt && \
    apk add tzdata && \
    apk add bash curl vim && \
    apk add coreutils bc && \
    /bin/sh && ln -sf /bin/bash /bin/sh && \
    /bin/ash && ln -sf /bin/bash /bin/ash && \
    rm -rf /var/cache/apk/*

RUN cp /usr/share/zoneinfo/America/Los_Angeles /etc/localtime && \
    echo "America/Los_Angeles" > /etc/timezone

COPY ./deploy                         /usr/local/deploy
COPY ./deploy/default/bashrc          /root/.bashrc
COPY ./deploy/default/vimrc           /root/.vimrc

COPY ./src                            /src
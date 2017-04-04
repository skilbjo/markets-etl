FROM gliderlabs/alpine:latest

ENV CONSUL_PREFIX __CONSUL_PREFIX__
ENV CONSUL_SECRET_PREFIX __CONSUL_SECRET_PREFIX__

RUN apk add -U && \
    postgresql-client && \
    postfix mutt && \
    tzdata && \
    bash bash-completion curl vim && \
    coreutils bc && \
    cp /usr/share/zoneinfo/America/Los_Angeles /etc/localtime && \
    echo "America/Los_Angeles" > /etc/timezone && \
    rm -rf /var/cache/apk/*

COPY deploy                         /usr/local/deploy
COPY deploy/default/bashrc          /root/.bashrc
COPY deploy/default/vimrc           /root/.vimrc

COPY src                            /src

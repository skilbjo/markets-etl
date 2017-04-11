FROM resin/armhf-alpine:3.5

COPY deploy                         /usr/local/deploy
COPY deploy/default/bashrc          /root/.bashrc
COPY deploy/default/vimrc           /root/.vimrc

COPY __UBERJAR__                    /app.jar

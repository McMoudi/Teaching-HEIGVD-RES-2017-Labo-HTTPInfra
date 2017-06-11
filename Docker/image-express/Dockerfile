FROM node:6.10.3

RUN apt-get update && \
    apt-get install -y vim

COPY src /opt/app

RUN ip addr show | grep inet

CMD ["node", "/opt/app/index.js"]
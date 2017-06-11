FROM php:7.0-apache

RUN apt-get update && \
    apt-get install -y vim

COPY conf/ /etc/apache2

RUN mkdir /var/www/balancer-manager
RUN a2enmod proxy proxy_http proxy_balancer lbmethod_byrequests
RUN a2ensite 000-*  002-*

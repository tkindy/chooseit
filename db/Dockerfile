FROM postgres

ENV POSTGRES_PASSWORD=admin
ENV POSTGRES_DB=chooseit

ADD local-user.sql /docker-entrypoint-initdb.d/

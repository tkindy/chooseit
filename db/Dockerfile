FROM postgres

ENV POSTGRES_PASSWORD=admin
ENV POSTGRES_DB=chooseit

ADD 1setup.sql 2local-user.sql /docker-entrypoint-initdb.d/

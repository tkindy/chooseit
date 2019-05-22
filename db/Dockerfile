FROM postgres

ENV POSTGRES_PASSWORD=admin
ENV POSTGRES_DB=chooseit

ADD setup.sql /docker-entrypoint-initdb.d/

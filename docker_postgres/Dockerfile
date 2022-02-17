FROM postgres:14.1
ENV POSTGRES_PASSWORD=123
COPY init.sql /docker-entrypoint-initdb.d/
EXPOSE 5432
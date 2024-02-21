FROM mysql:8.0

ENV MYSQL_ROOT_PASSWORD=ppssdd12345*
ENV MYSQL_DATABASE=kon

COPY users.sql /docker-entrypoint-initdb.d/
COPY users_name.sql /docker-entrypoint-initdb.d/
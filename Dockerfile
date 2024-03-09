# Use an official MySQL runtime as a parent image
FROM mysql:latest

# Set the root password for MySQL
ENV MYSQL_ROOT_PASSWORD=rootPassword

# Create a database and user for your application
ENV MYSQL_DATABASE=testdb
ENV MYSQL_USER=myuser
ENV MYSQL_PASSWORD=mypassword

## Copy the SQL script to initialize the database
#COPY ./init.sql /docker-entrypoint-initdb.d/

# Expose the MySQL port
EXPOSE 3306

# Define the default command to run when the container starts
CMD ["mysqld"]

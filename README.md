# JIm Vault

**JIm Vault** is a pet project designed for learning the basics of working with servlets and the AWS SDK. 
A small application to save all your images. 

## Requirements
To run this project, you will need:

- Java 17+
- Gradle
- AWS CLI
- LocalStack CLI
- Docker

## Setup and Running
### Steps to Run

1. **Run Docker Compose**
```sh
   docker-compose up
```
2. **Create S3 Bucket**
After starting Docker Compose, run one of the scripts to create an S3 bucket:

- For Windows:
```sh
    ./create-s3-bucket.bat
```
- For Unix-based systems:
```sh
    ./create-s3-bucket.sh
```
3. **Run the Application**
Execute the JImVaultApplication class to start the application.

## Notes
- Ensure that Docker and Docker Compose are properly installed and running.
- Configure AWS CLI with the correct credentials.
- The create-s3-bucket scripts will create an S3 bucket named "images" required by the application.
- Uploading files with sizes above 2 MB is currently not supported, it needs an additional setting somewhere in Tomcat.
- Free LocalStack Docker image doesn't support data persistence, so after restarting the LocalStack container
  your S3 images bucket will disappear. Recommended recreating both LocalStack and Postgres containers to prevent 404 bucket/key error.
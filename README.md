# *Images Uploader*
## Description
Application for upload and store images in AWS S3 storage.
## Requires
- Running PostgreSQL database in Docker
- Account in AWS
## How to run
- *PostgreSQL*
1. Install Docker
2. Run shell script from dev/start-postgres.sh
- *AWS S3 Service*
1. Register and confirm account in https://aws.amazon.com/
2. Sign in to the AWS Management Console
3. Create an access key in IAM service
4. Paste your access and secret key to code or as environment variables
5. Create a bucket in S3 service
6. Paste to code name of bucket and his region using Regions class
- *Run the code*
- *Open localhost:8082 in your browser*

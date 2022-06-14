# *Images Uploader*
## Description
Application for upload and store images in AWS S3 storage.
## Requires
- Running PostgreSQL database in Docker
- Account in AWS with created Bucket in S3 service
## How to run
- *PostgreSQL*
1. Install Docker
2. Run shell script from dev/start-postgres.sh in command stroke or shell
- *AWS S3 Service*
1. Register and confirm account in https://aws.amazon.com/
2. Sign in to the AWS Management Console
3. In IAM service create access key
4. Paste your access and secret key to code or as environment variables
5. In S3 service create bucket 
6. Paste to code name of bucket and his region using Regions class
- *Run the code*
- *Open localhost:8082 in your browser*
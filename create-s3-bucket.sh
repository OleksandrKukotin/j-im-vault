#!/bin/bash

# Set AWS CLI to point to LocalStack
export AWS_ACCESS_KEY_ID=test
export AWS_SECRET_ACCESS_KEY=test
export AWS_DEFAULT_REGION=us-east-1
export AWS_ENDPOINT_URL=http://s3.localhost.localstack.cloud:4566

# Wait for LocalStack to be ready
sleep 20

# Create the S3 bucket
aws --endpoint-url=$AWS_ENDPOINT_URL s3 mb s3://images

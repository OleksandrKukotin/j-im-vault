#!/bin/bash

# Set AWS CLI to point to LocalStack
export AWS_ACCESS_KEY_ID=test
export AWS_SECRET_ACCESS_KEY=test
export AWS_DEFAULT_REGION=us-east-1
export AWS_ENDPOINT_URL=http://s3.localhost.localstack.cloud:4566

# Create the S3 bucket
aws s3api create-bucket --bucket images
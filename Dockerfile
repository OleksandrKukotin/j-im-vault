FROM localstack/localstack

COPY create-s3-bucket.sh /docker-entrypoint-initaws.d/create-s3-bucket.sh
RUN chmod +x /docker-entrypoint-initaws.d/create-s3-bucket.sh

ENTRYPOINT [ "/bin/sh", "/docker-entrypoint-initaws.d/create-s3-bucket.sh" ]

docker stop pwd-service
docker rm pwd-service
docker build -t pwd-service:1.0.0 .
docker run -d --name pwd-service -p 443:8080 pwd-service:1.0.0
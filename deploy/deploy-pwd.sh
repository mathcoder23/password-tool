export SERVICE_VERSION=1.0.0
export MAIL_USERNAME=xx
export MAIL_PASSWORD=xx
export MAIL_RECEIVE_LIST=xx
export RSA_PRIVATE=xx
export RSA_PUBLIC=xx
docker stack deploy --with-registry-auth -c docker-compose-pwd-1.0.yml pwd
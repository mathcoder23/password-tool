FROM registry.cn-hangzhou.aliyuncs.com/mt23/jre8-alpine-shanghai:1.0.0
ADD app.jar /app.jar
#对于spring boot项目 强制设定端口为8080
ENTRYPOINT ["sh","-c","java -Dserver.port=8080 -Dspring.profiles.active=rc -jar /app.jar "]

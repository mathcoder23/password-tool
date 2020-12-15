# password-tool
个人密码加密解密服务

# 部署服务
部署前提：安装docker，并且初始化docker swarm
1，拷贝deploy文件夹下所有文件到服务器中，
2，配置参数vi deploy/deploy-pwd.sh
```
export SERVICE_VERSION=1.0.0
export MAIL_USERNAME=邮件发送端
export MAIL_PASSWORD=邮件发送端密码
export MAIL_RECEIVE_LIST=接收邮件列表，英文逗号分隔
export RSA_PRIVATE=rsa私钥
export RSA_PUBLIC=rsa公钥
```
rsa公钥的私钥暂时不配，需要等后端启动一次后生成后在配置
3，命令
chmod +x deploy/deploy-pwd.sh
./deploy/deploy-pwd.sh

部署成功后通过http访问8090端口
http://localhost:8090/doc.html 进入接口管理界面

4，生成密钥对
http://localhost:8090/doc.html#/default/password-rest/buildRsaKeyUsingPOST
生成完成后配置到环境变量后重新执行部署脚本。至此ok

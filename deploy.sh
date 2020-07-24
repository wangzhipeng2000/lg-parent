#!/bin/bash

#所有模块	
models=("lg-register"  "lg-user"  "lg-auth"  "lg-order"  "lg-other"  "lg-product" "lg-gateway");
echo deploy models: $models
#部署目录
deployPath=/home/1711d/deploy/
#项目打包,跳过测试
mvn clean package -Dmaven.test.skip=true
#拷贝jar包到服务器
for m in ${models[@]} ;  
do
  echo $m  
  scp ./$m/target/$m-1.0-SNAPSHOT.jar root@serverIp:$deployPath
done
#执行远程命令,启动服务
#scp kill.sh root@serverIp:$deployPath

# nohup java -Xms64M -Xmx64M -jar $m-1.0-SNAPSHOT.jar > ./logs/$m.log &


#nohup java -Xms64M -Xmx64M -jar lg-register-1.0-SNAPSHOT.jar > ./logs/lg-register.log &
#sleep 10
#nohup java -Xms64M -Xmx64M -jar lg-user-1.0-SNAPSHOT.jar > ./logs/lg-user.log &
#sleep 10
#nohup java -Xms64M -Xmx64M -jar lg-auth-1.0-SNAPSHOT.jar > ./logs/lg-auth.log &
#sleep 10
#nohup java -Xms64M -Xmx64M -jar lg-gateway-1.0-SNAPSHOT.jar > ./logs/lg-gateway.log &


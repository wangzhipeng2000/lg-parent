#!/bin/bash

#����ģ��	
models=("lg-register"  "lg-user"  "lg-auth"  "lg-order"  "lg-other"  "lg-product" "lg-gateway");
echo deploy models: $models
#����Ŀ¼
deployPath=/home/1711d/deploy/
#��Ŀ���,��������
mvn clean package -Dmaven.test.skip=true
#����jar����������
for m in ${models[@]} ;  
do
  echo $m  
  scp ./$m/target/$m-1.0-SNAPSHOT.jar root@serverIp:$deployPath
done
#ִ��Զ������,��������
#scp kill.sh root@serverIp:$deployPath

# nohup java -Xms64M -Xmx64M -jar $m-1.0-SNAPSHOT.jar > ./logs/$m.log &


#nohup java -Xms64M -Xmx64M -jar lg-register-1.0-SNAPSHOT.jar > ./logs/lg-register.log &
#sleep 10
#nohup java -Xms64M -Xmx64M -jar lg-user-1.0-SNAPSHOT.jar > ./logs/lg-user.log &
#sleep 10
#nohup java -Xms64M -Xmx64M -jar lg-auth-1.0-SNAPSHOT.jar > ./logs/lg-auth.log &
#sleep 10
#nohup java -Xms64M -Xmx64M -jar lg-gateway-1.0-SNAPSHOT.jar > ./logs/lg-gateway.log &


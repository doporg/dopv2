# DOP网络结构

## 网址：

Jenkins: jenkins.dop.clsaa.com

harbor: https://registry.dop.clsaa.com

eureka: discovery.dop.clsaa.com

gitlab: http://gitlab.dop.clsaa.com

gateway: open.dop.clsaa.com

dop-web: www.dop.clsaa.com

## Node1：

**Nginx**：

- 端口：80,443

- 转发的服务：

  1. **jenkins**，node3,8080端口
  2.  harbor,  node3, 6443端口
  3.  gitlab, node2,  30808端口

- 启动方式：systemctl start nginx

**mysql**:

- 端口：30306(暴露的node3的端口)
- 启动方式：cd /opt/tools   kubectl apply -f mysql.yaml  kubectl apply -f mysql-pv.yaml  kubectl apply -f mysql-pvc.yaml 

- 访问：nodeIp：nodeport



**Redis**：

- 端口：6379 
- 访问方式：直接访问
- 启动方式：docker run -d -p 6379:6379 --name redis-test redis /bin/bash -c "/usr/local/bin/redis-server"



## Node2：

**gitlab**：

- 端口：30808端口
- 操作方式：gitlab-ctl 




## Node3:

**harbor**:

- 端口：6443
- 启动方式：cd /mnt/tools/harbor   ./install.sh





**ingress**：

- 端口：80端口

- 启动方式：cd /mnt/tools/ingress kubectl apply -f ingress.yaml

- 转发的服务：

  1. discovery-server
  2. gateway-server
  3. dop-web

  






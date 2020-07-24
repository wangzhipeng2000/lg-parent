#!/bin/bash
ssh root@serverIp <<  remotessh
cd /home/1711d/deploy/logs/
tail -f lg-auth.log lg-gateway.log lg-register.log lg-user.log
remotessh
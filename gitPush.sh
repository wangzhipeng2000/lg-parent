#!/bin/bash
# shell�ű����ύ��Ŀ��Զ��git�ֿ�
# ��Ҫ���commit ˵��
commitM=$1
#��������ύ˵��
if [ $commitM == ""];then
  echo "Push failure, please add instructions.";
else
  git add pom.xml gitPush.sh deploy.sh tail-f.sh ./sql/;
  
  models=("lg-auth" "lg-common"  "lg-gateway"  "lg-order"  "lg-other"  "lg-product"  "lg-register"  "lg-user" "lg-user-interface");
  for m in ${models[@]} ;  
  do  
	git add ./$m/pom.xml ./$m/src;
  done
  
  echo "commit local repository";
  git commit -m "$commitM";
  echo "commit remote repository";
  git push;
  echo "modify��$commitM,push sucessful.";
fi


#!/bin/bash 
echo "输入提交说明:"
read COMMIT
mysqldump --add-drop-database=TRUE -uroot -pkb123456 PropertySystem > PropertySystem.sql
echo "数据库已备份..."
git add .
git commit -m $COMMIT
echo "已提交到本地..."
git push mine master
echo "提交成功"

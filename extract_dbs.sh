#!/bin/bash
# 提取所有数据库名称并创建
cd "D:/毕业设计/2026-biyesheji"

echo "-- 自动提取的数据库列表" > all_databases.sql
echo "-- 生成时间: $(date)" >> all_databases.sql
echo "" >> all_databases.sql

for dir in *-backend; do
    yml=$(find "$dir/src/main/resources" -name "application*.yml" -o -name "application*.yaml" 2>/dev/null | head -1)
    if [ -n "$yml" ]; then
        db=$(grep "jdbc:mysql" "$yml" 2>/dev/null | grep -oP '[^/]+\?(?=[^ ]*)' | head -1 | sed 's/?.*//')
        if [ -n "$db" ]; then
            echo "CREATE DATABASE IF NOT EXISTS \`$db\` DEFAULT CHARACTER SET utf8mb4;" >> all_databases.sql
        fi
    fi
done

echo "" >> all_databases.sql
echo "-- 共 $(grep -c "CREATE DATABASE" all_databases.sql) 个数据库" >> all_databases.sql

echo "已生成 all_databases.sql"
cat all_databases.sql

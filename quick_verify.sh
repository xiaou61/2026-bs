#!/bin/bash
# 快速验证脚本 - 检查项目结构完整性
cd "D:/毕业设计/2026-biyesheji"
mkdir -p verification

BACKEND_REPORT="verification/backend_structure.txt"
FRONTEND_REPORT="verification/frontend_structure.txt"

# ========== 后端结构检查 ==========
echo "=== 后端项目结构检查 ===" > "$BACKEND_REPORT"
echo "检查时间: $(date)" >> "$BACKEND_REPORT"
echo "" >> "$BACKEND_REPORT"

BACK_OK=0
BACK_WARN=0
BACK_ERR=0

for dir in $(ls -d *-backend 2>/dev/null | sort); do
    issues=""

    # 检查pom.xml
    [ ! -f "$dir/pom.xml" ] && issues="${issues}无pom.xml; "

    # 检查application.yml
    YML_FILE=$(find "$dir/src/main/resources" -name "application*.yml" -o -name "application*.yaml" 2>/dev/null | head -1)
    [ -z "$YML_FILE" ] && issues="${issues}无application.yml; "

    # 检查主启动类
    MAIN_CLASS=$(find "$dir/src/main/java" -name "*Application.java" 2>/dev/null | head -1)
    [ -z "$MAIN_CLASS" ] && issues="${issues}无Application主类; "

    # 检查controller目录
    [ ! -d "$dir/src/main/java"/*/controller ] && [ ! -d "$dir/src/main/java"/*/*/controller ] && issues="${issues}无controller目录; "

    # 检查数据库配置
    if [ -n "$YML_FILE" ]; then
        DB_NAME=$(grep "jdbc:mysql" "$YML_FILE" 2>/dev/null | grep -oP '[^/]+\?[^ ]*' | head -1 | sed 's/?.*//')
        [ -z "$DB_NAME" ] && issues="${issues}数据库URL未配置; "
    fi

    if [ -z "$issues" ]; then
        echo "OK: $dir" >> "$BACKEND_REPORT"
        BACK_OK=$((BACK_OK + 1))
    else
        echo "WARN: $dir - $issues" >> "$BACKEND_REPORT"
        BACK_WARN=$((BACK_WARN + 1))
    fi
done

echo "" >> "$BACKEND_REPORT"
echo "=== 统计 ===" >> "$BACKEND_REPORT"
echo "正常: $BACK_OK 问题: $BACK_WARN" >> "$BACKEND_REPORT"

# ========== 前端结构检查 ==========
echo "=== 前端项目结构检查 ===" > "$FRONTEND_REPORT"
echo "检查时间: $(date)" >> "$FRONTEND_REPORT"
echo "" >> "$FRONTEND_REPORT"

FRONT_OK=0
FRONT_WARN=0

for dir in $(ls -d *-frontend 2>/dev/null | sort); do
    issues=""

    # 检查package.json
    [ ! -f "$dir/package.json" ] && issues="${issues}无package.json; "

    # 检查src目录
    [ ! -d "$dir/src" ] && issues="${issues}无src目录; "

    # 检查vite.config
    [ ! -f "$dir/vite.config.js" ] && [ ! -f "$dir/vite.config.ts" ] && issues="${issues}无vite.config; "

    # 检查Login页面
    LOGIN_FILE=$(find "$dir/src" -iname "*login*" 2>/dev/null | head -1)
    [ -z "$LOGIN_FILE" ] && issues="${issues}无Login页面; "

    # 检查API配置
    API_FILE=$(find "$dir/src" -iname "request.*" -o -iname "api.*" 2>/dev/null | head -1)
    [ -z "$API_FILE" ] && issues="${issues}无API配置; "

    if [ -z "$issues" ]; then
        echo "OK: $dir" >> "$FRONTEND_REPORT"
        FRONT_OK=$((FRONT_OK + 1))
    else
        echo "WARN: $dir - $issues" >> "$FRONTEND_REPORT"
        FRONT_WARN=$((FRONT_WARN + 1))
    fi
done

echo "" >> "$FRONTEND_REPORT"
echo "=== 统计 ===" >> "$FRONTEND_REPORT"
echo "正常: $FRONT_OK 问题: $FRONT_WARN" >> "$FRONTEND_REPORT"

echo "结构检查完成!"
echo "后端: 正常=$BACK_OK 问题=$BACK_WARN"
echo "前端: 正常=$FRONT_OK 问题=$FRONT_WARN"

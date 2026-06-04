#!/bin/bash
# 前端批量构建验证脚本
# 用法: bash verify_frontend_batch.sh [起始编号] [结束编号]

cd "D:/毕业设计/2026-biyesheji"

START=${1:-1}
END=${2:-200}
RESULTS_FILE="D:/毕业设计/2026-biyesheji/verification/frontend_${START}_${END}.txt"
mkdir -p "D:/毕业设计/2026-biyesheji/verification"

echo "=== 前端构建验证 [$START-$END] ===" > "$RESULTS_FILE"
echo "开始时间: $(date)" >> "$RESULTS_FILE"
echo "" >> "$RESULTS_FILE"

PASS=0
FAIL=0
SKIP=0
TOTAL=0

for num in $(seq $START $END); do
    dir=$(printf "%03d-frontend" $num)

    if [ ! -d "$dir" ]; then
        continue
    fi

    TOTAL=$((TOTAL + 1))

    # 检查是否是Vue/Vite项目
    if [ ! -f "$dir/package.json" ]; then
        # 检查是否是静态HTML或小程序项目
        if [ -f "$dir/index.html" ]; then
            echo "SKIP: $dir - 静态HTML项目" >> "$RESULTS_FILE"
        elif [ -f "$dir/app.json" ]; then
            echo "SKIP: $dir - 微信小程序项目" >> "$RESULTS_FILE"
        else
            echo "SKIP: $dir - 无package.json" >> "$RESULTS_FILE"
        fi
        SKIP=$((SKIP + 1))
        continue
    fi

    echo -n "[$TOTAL] $dir ... "

    cd "$dir"

    # 检查node_modules，不存在则安装
    if [ ! -d "node_modules" ]; then
        timeout 120 npm install --silent 2>/dev/null
        if [ $? -ne 0 ]; then
            echo "FAIL (npm install)"
            echo "FAIL: $dir - npm install失败" >> "$RESULTS_FILE"
            FAIL=$((FAIL + 1))
            cd ..
            continue
        fi
    fi

    # 尝试构建
    timeout 120 npm run build --silent 2>/dev/null
    RESULT=$?

    if [ $RESULT -eq 0 ]; then
        echo "PASS"
        echo "PASS: $dir" >> "$RESULTS_FILE"
        PASS=$((PASS + 1))
    elif [ $RESULT -eq 124 ]; then
        echo "TIMEOUT"
        echo "TIMEOUT: $dir" >> "$RESULTS_FILE"
        FAIL=$((FAIL + 1))
    else
        echo "FAIL"
        echo "FAIL: $dir" >> "$RESULTS_FILE"
        FAIL=$((FAIL + 1))
    fi

    cd ..
done

echo "" >> "$RESULTS_FILE"
echo "=== 统计 ===" >> "$RESULTS_FILE"
echo "总计: $TOTAL 通过: $PASS 失败: $FAIL 跳过: $SKIP" >> "$RESULTS_FILE"
echo "结束时间: $(date)" >> "$RESULTS_FILE"

echo ""
echo "[$START-$END] 完成! 总计=$TOTAL 通过=$PASS 失败=$FAIL 跳过=$SKIP"

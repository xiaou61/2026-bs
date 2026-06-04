#!/bin/bash
# 后端批量编译验证 - 分批并行
# 用法: bash verify_backend_batch.sh [起始编号] [结束编号]
# 例如: bash verify_backend_batch.sh 1 50

cd "D:/毕业设计/2026-biyesheji"

START=${1:-1}
END=${2:-200}
RESULTS_FILE="verification/backend_${START}_${END}.txt"
mkdir -p verification

echo "=== 后端编译验证 [$START-$END] ===" > "$RESULTS_FILE"
echo "开始时间: $(date)" >> "$RESULTS_FILE"
echo "" >> "$RESULTS_FILE"

PASS=0
FAIL=0
SKIP=0
TOTAL=0

for num in $(seq $START $END); do
    # 格式化编号
    dir=$(printf "%03d-backend" $num)

    if [ ! -d "$dir" ]; then
        continue
    fi

    TOTAL=$((TOTAL + 1))

    if [ ! -f "$dir/pom.xml" ]; then
        echo "SKIP: $dir - 无pom.xml" >> "$RESULTS_FILE"
        SKIP=$((SKIP + 1))
        continue
    fi

    echo -n "[$TOTAL] $dir ... "

    cd "$dir"

    # 先离线编译
    timeout 60 mvn compile -q -o 2>/dev/null
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
        # 在线重试
        timeout 120 mvn compile -q 2>/dev/null
        RESULT2=$?
        if [ $RESULT2 -eq 0 ]; then
            echo "PASS(online)"
            echo "PASS: $dir (在线)" >> "$RESULTS_FILE"
            PASS=$((PASS + 1))
        else
            echo "FAIL"
            # 获取错误摘要
            ERRORS=$(timeout 120 mvn compile 2>&1 | grep -i "error\|ERROR\|失败" | head -3)
            echo "FAIL: $dir" >> "$RESULTS_FILE"
            echo "  $ERRORS" >> "$RESULTS_FILE"
            FAIL=$((FAIL + 1))
        fi
    fi

    cd ..
done

echo "" >> "$RESULTS_FILE"
echo "=== 统计 ===" >> "$RESULTS_FILE"
echo "总计: $TOTAL 通过: $PASS 失败: $FAIL 跳过: $SKIP" >> "$RESULTS_FILE"
echo "结束时间: $(date)" >> "$RESULTS_FILE"

echo ""
echo "[$START-$END] 完成! 总计=$TOTAL 通过=$PASS 失败=$FAIL 跳过=$SKIP"

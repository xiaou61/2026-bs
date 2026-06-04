#!/bin/bash
# 后端批量编译验证脚本
# 用法: bash verify_backends.sh

RESULTS_FILE="backend_verify_results.txt"
echo "=== 后端编译验证报告 ===" > "$RESULTS_FILE"
echo "开始时间: $(date)" >> "$RESULTS_FILE"
echo "" >> "$RESULTS_FILE"

PASS=0
FAIL=0
SKIP=0

for dir in *-backend; do
    if [ ! -f "$dir/pom.xml" ]; then
        echo "SKIP: $dir - 无pom.xml" >> "$RESULTS_FILE"
        SKIP=$((SKIP + 1))
        continue
    fi

    echo -n "验证 $dir ... "

    # 尝试编译，超时120秒
    cd "$dir"
    timeout 120 mvn compile -q -o 2>&1 > /tmp/mvn_output.txt
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
        # 离线失败，在线重试
        timeout 180 mvn compile -q 2>&1 > /tmp/mvn_output.txt
        RESULT2=$?
        if [ $RESULT2 -eq 0 ]; then
            echo "PASS (online)"
            echo "PASS: $dir (需要在线下载依赖)" >> "$RESULTS_FILE"
            PASS=$((PASS + 1))
        else
            echo "FAIL"
            echo "FAIL: $dir" >> "$RESULTS_FILE"
            tail -5 /tmp/mvn_output.txt >> "$RESULTS_FILE"
            echo "" >> "$RESULTS_FILE"
            FAIL=$((FAIL + 1))
        fi
    fi

    cd ..
done

echo "" >> "$RESULTS_FILE"
echo "=== 统计 ===" >> "$RESULTS_FILE"
echo "通过: $PASS" >> "$RESULTS_FILE"
echo "失败: $FAIL" >> "$RESULTS_FILE"
echo "跳过: $SKIP" >> "$RESULTS_FILE"
echo "结束时间: $(date)" >> "$RESULTS_FILE"

echo ""
echo "验证完成! 通过=$PASS 失败=$FAIL 跳过=$SKIP"
echo "详细结果: $RESULTS_FILE"

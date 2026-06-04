#!/bin/bash
# 全量验证脚本 - 后端编译 + 前端构建
# 用法: bash verify_all.sh

cd "D:/毕业设计/2026-biyesheji"

BACKEND_RESULTS="verification/backend_results.txt"
FRONTEND_RESULTS="verification/frontend_results.txt"
SUMMARY="verification/summary.txt"

mkdir -p verification

# ========== 后端编译验证 ==========
echo "=== 后端编译验证 ===" > "$BACKEND_RESULTS"
echo "开始时间: $(date)" >> "$BACKEND_RESULTS"
echo "" >> "$BACKEND_RESULTS"

BACK_PASS=0
BACK_FAIL=0
BACK_SKIP=0
BACK_TOTAL=0

for dir in $(ls -d *-backend 2>/dev/null | sort); do
    BACK_TOTAL=$((BACK_TOTAL + 1))

    if [ ! -f "$dir/pom.xml" ]; then
        echo "SKIP: $dir - 无pom.xml" >> "$BACKEND_RESULTS"
        BACK_SKIP=$((BACK_SKIP + 1))
        continue
    fi

    echo -n "[$BACK_TOTAL] 验证 $dir ... "

    cd "$dir"

    # 先尝试离线编译(快)
    timeout 90 mvn compile -q -o 2>/dev/null
    RESULT=$?

    if [ $RESULT -eq 0 ]; then
        echo "PASS (offline)"
        echo "PASS: $dir" >> "$BACKEND_RESULTS"
        BACK_PASS=$((BACK_PASS + 1))
    elif [ $RESULT -eq 124 ]; then
        echo "TIMEOUT"
        echo "TIMEOUT: $dir" >> "$BACKEND_RESULTS"
        BACK_FAIL=$((BACK_FAIL + 1))
    else
        # 离线失败，在线重试
        timeout 180 mvn compile -q 2>/dev/null
        RESULT2=$?
        if [ $RESULT2 -eq 0 ]; then
            echo "PASS (online)"
            echo "PASS: $dir (在线)" >> "$BACKEND_RESULTS"
            BACK_PASS=$((BACK_PASS + 1))
        else
            echo "FAIL"
            echo "FAIL: $dir" >> "$BACKEND_RESULTS"
            # 记录错误信息
            timeout 180 mvn compile 2>&1 | tail -10 >> "$BACKEND_RESULTS"
            echo "" >> "$BACKEND_RESULTS"
            BACK_FAIL=$((BACK_FAIL + 1))
        fi
    fi

    cd ..
done

echo "" >> "$BACKEND_RESULTS"
echo "通过: $BACK_PASS / 失败: $BACK_FAIL / 跳过: $BACK_SKIP" >> "$BACKEND_RESULTS"

# ========== 前端构建验证 ==========
echo "" >> "$FRONTEND_RESULTS"
echo "=== 前端构建验证 ===" >> "$FRONTEND_RESULTS"
echo "开始时间: $(date)" >> "$FRONTEND_RESULTS"

FRONT_PASS=0
FRONT_FAIL=0
FRONT_SKIP=0
FRONT_TOTAL=0

for dir in $(ls -d *-frontend 2>/dev/null | sort); do
    FRONT_TOTAL=$((FRONT_TOTAL + 1))

    if [ ! -f "$dir/package.json" ]; then
        echo "SKIP: $dir - 无package.json" >> "$FRONTEND_RESULTS"
        FRONT_SKIP=$((FRONT_SKIP + 1))
        continue
    fi

    echo -n "[$FRONT_TOTAL] 验证 $dir ... "

    cd "$dir"

    # 检查node_modules是否存在，不存在则安装
    if [ ! -d "node_modules" ]; then
        timeout 120 npm install --silent 2>/dev/null
        if [ $? -ne 0 ]; then
            echo "FAIL (npm install)"
            echo "FAIL: $dir - npm install失败" >> "$FRONTEND_RESULTS"
            FRONT_FAIL=$((FRONT_FAIL + 1))
            cd ..
            continue
        fi
    fi

    # 尝试构建
    timeout 120 npm run build --silent 2>/dev/null
    RESULT=$?

    if [ $RESULT -eq 0 ]; then
        echo "PASS"
        echo "PASS: $dir" >> "$FRONTEND_RESULTS"
        FRONT_PASS=$((FRONT_PASS + 1))
    elif [ $RESULT -eq 124 ]; then
        echo "TIMEOUT"
        echo "TIMEOUT: $dir" >> "$FRONTEND_RESULTS"
        FRONT_FAIL=$((FRONT_FAIL + 1))
    else
        echo "FAIL"
        echo "FAIL: $dir" >> "$FRONTEND_RESULTS"
        FRONT_FAIL=$((FRONT_FAIL + 1))
    fi

    cd ..
done

echo "" >> "$FRONTEND_RESULTS"
echo "通过: $FRONT_PASS / 失败: $FRONT_FAIL / 跳过: $FRONT_SKIP" >> "$FRONTEND_RESULTS"

# ========== 汇总 ==========
echo "=== 验证汇总报告 ===" > "$SUMMARY"
echo "完成时间: $(date)" >> "$SUMMARY"
echo "" >> "$SUMMARY"
echo "后端编译:" >> "$SUMMARY"
echo "  总计: $BACK_TOTAL" >> "$SUMMARY"
echo "  通过: $BACK_PASS" >> "$SUMMARY"
echo "  失败: $BACK_FAIL" >> "$SUMMARY"
echo "  跳过: $BACK_SKIP" >> "$SUMMARY"
echo "" >> "$SUMMARY"
echo "前端构建:" >> "$SUMMARY"
echo "  总计: $FRONT_TOTAL" >> "$SUMMARY"
echo "  通过: $FRONT_PASS" >> "$SUMMARY"
echo "  失败: $FRONT_FAIL" >> "$SUMMARY"
echo "  跳过: $FRONT_SKIP" >> "$SUMMARY"

echo ""
echo "========================================="
echo "验证全部完成!"
echo "后端: 通过=$BACK_PASS 失败=$BACK_FAIL 跳过=$BACK_SKIP"
echo "前端: 通过=$FRONT_PASS 失败=$FRONT_FAIL 跳过=$FRONT_SKIP"
echo "详细结果: verification/"
echo "========================================="

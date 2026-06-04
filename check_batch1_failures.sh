#!/bin/bash
# Check batch 1 frontend build results in detail
cd "D:/毕业设计/2026-biyesheji"

echo "=== Batch 1 Frontend Build Detail Check ==="
echo ""

for num in $(seq 1 50); do
    dir=$(printf "%03d-frontend" $num)
    if [ ! -d "$dir" ]; then
        continue
    fi
    if [ ! -f "$dir/package.json" ]; then
        continue
    fi

    cd "$dir"

    # Check if node_modules exists
    if [ ! -d "node_modules" ]; then
        echo "SKIP: $dir - no node_modules"
        cd ..
        continue
    fi

    # Try build
    result=$(timeout 90 npm run build 2>&1)
    build_exit=$?

    if [ $build_exit -eq 0 ]; then
        echo "PASS: $dir"
    elif [ $build_exit -eq 124 ]; then
        echo "TIMEOUT: $dir"
    else
        echo "FAIL: $dir"
        echo "  Last 3 lines of error:"
        echo "$result" | tail -3 | sed 's/^/  /'
    fi

    cd ..
done

echo ""
echo "=== Done ==="

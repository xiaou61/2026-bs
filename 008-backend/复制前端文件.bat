@echo off
chcp 65001
echo ================================================
echo 智能菜谱推荐系统 - 前端文件整合脚本
echo ================================================
echo.

set SOURCE=..\008-frontend
set TARGET=src\main\resources\static

echo 正在复制前端文件到后端项目...
echo.

if not exist "%TARGET%\pages" mkdir "%TARGET%\pages"
if not exist "%TARGET%\pages\user" mkdir "%TARGET%\pages\user"
if not exist "%TARGET%\pages\admin" mkdir "%TARGET%\pages\admin"

echo [1/3] 复制用户端页面...
xcopy /Y /Q "%SOURCE%\pages\user\*.html" "%TARGET%\pages\user\"

echo [2/3] 复制管理端页面...
xcopy /Y /Q "%SOURCE%\pages\admin\*.html" "%TARGET%\pages\admin\"

echo [3/3] 复制JS文件...
xcopy /Y /Q "%SOURCE%\assets\js\*.js" "%TARGET%\js\"

echo.
echo ================================================
echo 文件复制完成！
echo ================================================
echo.
echo 下一步：
echo 1. 确保MySQL已启动
echo 2. 执行数据库脚本: src/main/resources/sql/schema.sql
echo 3. 修改 application.yml 中的数据库配置
echo 4. 运行 SmartRecipeApplication.java
echo 5. 访问 http://localhost:8008
echo.
echo 测试账户：
echo   管理员: admin / 123456
echo   用户: user1 / 123456
echo.
pause


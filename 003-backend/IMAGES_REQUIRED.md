# 图片资源需求清单

## 📁 目录结构

```
003-backend/src/main/resources/static/images/
└── products/
    ├── tomato.jpg          # 西红柿
    ├── cabbage.jpg         # 白菜
    ├── apple.jpg           # 苹果
    ├── rice.jpg            # 大米
    ├── egg.jpg             # 鸡蛋
    ├── orange.jpg          # 脐橙
    └── default.jpg         # 默认图片（可选）
```

---

## 📋 图片清单

### 必需图片（6张）

| 序号 | 文件名 | 用途 | 建议尺寸 | 说明 |
|------|--------|------|----------|------|
| 1 | `tomato.jpg` | 有机西红柿商品图 | 800x600px | 红色新鲜西红柿 |
| 2 | `cabbage.jpg` | 新鲜大白菜商品图 | 800x600px | 绿色大白菜 |
| 3 | `apple.jpg` | 烟台红富士苹果商品图 | 800x600px | 红色苹果 |
| 4 | `rice.jpg` | 东北大米商品图 | 800x600px | 大米或稻谷 |
| 5 | `egg.jpg` | 农家散养鸡蛋商品图 | 800x600px | 鸡蛋 |
| 6 | `orange.jpg` | 新鲜脐橙商品图 | 800x600px | 橙色脐橙 |

### 可选图片（1张）

| 序号 | 文件名 | 用途 | 建议尺寸 | 说明 |
|------|--------|------|----------|------|
| 7 | `default.jpg` | 默认商品图 | 800x600px | 通用农产品图标 |

---

## 🎨 图片规格要求

### 尺寸规范
- **推荐尺寸**：800x600px（4:3 比例）
- **最小尺寸**：600x450px
- **最大尺寸**：1200x900px
- **缩略图尺寸**：300x200px（前端自动缩放）

### 文件规范
- **格式**：JPG 或 PNG
- **大小**：每张不超过 500KB
- **命名**：小写英文，使用连字符

### 质量要求
- 清晰度：高清，无模糊
- 背景：纯色或简洁背景
- 主体：商品居中，占画面 60-80%
- 光线：明亮自然

---

## 🔧 快速解决方案

### 方案一：使用占位图（当前默认）

项目已配置自动降级到占位图服务，如果图片不存在会自动显示：
```
https://via.placeholder.com/300x200?text=商品名称
```

**优点**：无需准备图片即可运行
**缺点**：不够美观

### 方案二：使用免费图库

推荐以下免费图库网站：

1. **Unsplash**（高质量）
   - 网址：https://unsplash.com
   - 搜索：tomato, cabbage, apple, rice, egg, orange

2. **Pexels**（免费商用）
   - 网址：https://www.pexels.com
   - 中文搜索支持

3. **Pixabay**（完全免费）
   - 网址：https://pixabay.com
   - 无需注册

### 方案三：AI 生成图片

使用 AI 工具生成农产品图片：
- Midjourney
- DALL-E
- Stable Diffusion

---

## 📥 图片存放位置

将下载的图片放到以下目录：

```
D:\毕业设计\2026-biyesheji\003-backend\src\main\resources\static\images\products\
```

**完整路径示例：**
```
D:\毕业设计\2026-biyesheji\003-backend\src\main\resources\static\images\products\tomato.jpg
D:\毕业设计\2026-biyesheji\003-backend\src\main\resources\static\images\products\cabbage.jpg
...
```

---

## 🚀 快速准备步骤

### 步骤 1：创建目录
```bash
cd 003-backend/src/main/resources/static
mkdir images
cd images
mkdir products
```

### 步骤 2：下载图片

访问免费图库，搜索并下载以下关键词的图片：
1. `tomato` 或 `西红柿`
2. `cabbage` 或 `白菜`
3. `apple` 或 `苹果`
4. `rice` 或 `大米`
5. `egg` 或 `鸡蛋`
6. `orange` 或 `橙子`

### 步骤 3：重命名文件

下载后重命名为：
- `tomato.jpg`
- `cabbage.jpg`
- `apple.jpg`
- `rice.jpg`
- `egg.jpg`
- `orange.jpg`

### 步骤 4：放入目录

将所有图片复制到：
```
003-backend/src/main/resources/static/images/products/
```

### 步骤 5：重启项目

重启 Spring Boot 应用，图片即可显示。

---

## ⚠️ 注意事项

1. **图片访问路径**
   - 数据库存储：`/images/products/tomato.jpg`
   - 实际存放：`src/main/resources/static/images/products/tomato.jpg`
   - 访问 URL：`http://localhost:8080/images/products/tomato.jpg`

2. **缓存问题**
   - 如果更新图片后不显示，清除浏览器缓存
   - 或使用 Ctrl + F5 强制刷新

3. **图片大小**
   - 控制文件大小在 500KB 以内
   - 大文件会影响加载速度

4. **版权问题**
   - 使用免费商用图片
   - 或自己拍摄/制作图片

---

## 🎯 推荐图片资源（直接下载）

### Unsplash 直链（示例）

```
西红柿：https://unsplash.com/photos/red-tomato-fruit-on-white-surface-_E1PQXKUkMw
白菜：https://unsplash.com/s/photos/cabbage
苹果：https://unsplash.com/s/photos/red-apple
大米：https://unsplash.com/s/photos/rice
鸡蛋：https://unsplash.com/s/photos/eggs
橙子：https://unsplash.com/s/photos/orange-fruit
```

---

## ❓ 常见问题

### Q1: 图片不显示怎么办？
**A:** 
1. 检查文件名是否正确（区分大小写）
2. 检查目录路径是否正确
3. 检查文件权限
4. 重启项目
5. 清除浏览器缓存

### Q2: 可以使用 PNG 格式吗？
**A:** 可以，但需要修改数据库中的路径（.jpg 改为 .png）

### Q3: 能不能用网络图片？
**A:** 可以，直接在数据库中填写完整的 URL，如：
```
https://example.com/images/tomato.jpg
```

### Q4: 不想准备图片可以吗？
**A:** 可以，项目已设置占位图降级，会自动显示默认图片。

---

## 📞 技术支持

如果遇到图片相关问题：
1. 查看浏览器控制台错误信息
2. 检查 Spring Boot 启动日志
3. 验证静态资源配置（WebConfig.java）

---

**祝使用愉快！** 🎉


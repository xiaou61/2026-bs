# 项目协作约定

- 每次前端执行 `npm install`、`npm run build` 等构建验证后，确认结果并删除对应项目的 `node_modules` 目录。
- 批量构建多个前端项目时，收尾阶段统一扫描并清理仓库内所有 `node_modules`，避免依赖目录长期留在项目中。

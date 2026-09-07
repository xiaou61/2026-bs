<template>
  <DataPage title="产品分类" description="农产品分类、监管标准和检测要求管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getProductCategoryPage, addProductCategory, updateProductCategory, deleteProductCategory, enableProductCategory, disableProductCategory } from '../api'
const api = { page: getProductCategoryPage, add: addProductCategory, update: updateProductCategory, delete: deleteProductCategory }
const columns = [{"prop": "categoryName", "label": "分类名称"}, {"prop": "categoryCode", "label": "分类编码"}, {"prop": "standardName", "label": "执行标准"}, {"prop": "inspectionRule", "label": "检测要求"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "riskLevel", "label": "风险等级"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "categoryName", "label": "分类名称"}, {"prop": "categoryCode", "label": "分类编码"}, {"prop": "standardName", "label": "执行标准"}, {"prop": "inspectionRule", "label": "检测要求", "type": "textarea"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "riskLevel", "label": "风险等级", "type": "select", "options": [{"label": "LOW", "value": "LOW"}, {"label": "MEDIUM", "value": "MEDIUM"}, {"label": "HIGH", "value": "HIGH"}]}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}]}]
const rowActions = [{"command": "enable", "label": "启用", "type": "primary"}, {"command": "disable", "label": "停用", "type": "primary"}]
const defaults = {"riskLevel": "LOW", "status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'enable') await enableProductCategory(row.id)
  if (command === 'disable') await disableProductCategory(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>

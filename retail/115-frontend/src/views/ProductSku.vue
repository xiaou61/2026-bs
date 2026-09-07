<template>
  <DataPage title="商品SKU" description="跨境商品SKU、品类、HS编码、原产国和申报价值管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getProductSkuPage, addProductSku, updateProductSku, deleteProductSku, publishProductSku, disableProductSku } from '../api'
const api = { page: getProductSkuPage, add: addProductSku, update: updateProductSku, delete: deleteProductSku }
const columns = [{"prop": "skuNo", "label": "SKU编号"}, {"prop": "productName", "label": "商品名称"}, {"prop": "categoryName", "label": "品类"}, {"prop": "hsCode", "label": "HS编码"}, {"prop": "originCountry", "label": "原产国"}, {"prop": "declaredValue", "label": "申报价值"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "skuNo", "label": "SKU编号"}, {"prop": "productName", "label": "商品名称"}, {"prop": "categoryName", "label": "品类"}, {"prop": "hsCode", "label": "HS编码"}, {"prop": "originCountry", "label": "原产国"}, {"prop": "declaredValue", "label": "申报价值", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "DRAFT", "value": "DRAFT"}, {"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}]}]
const rowActions = [{"command": "publish", "label": "发布", "type": "success"}, {"command": "disable", "label": "下架", "type": "danger"}]
const defaults = {"status": "DRAFT"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'publish') await publishProductSku(row.id)
  if (command === 'disable') await disableProductSku(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>

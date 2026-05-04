<template>
  <DataPage title="产品批次" description="农产品批次、基地、采收日期、溯源码和上链状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getProductBatchPage, addProductBatch, updateProductBatch, deleteProductBatch, submitProductBatch, chainProductBatch, closeProductBatch } from '../api'
const api = { page: getProductBatchPage, add: addProductBatch, update: updateProductBatch, delete: deleteProductBatch }
const columns = [{"prop": "batchNo", "label": "批次编号"}, {"prop": "productName", "label": "产品名称"}, {"prop": "baseName", "label": "基地"}, {"prop": "farmerName", "label": "农户"}, {"prop": "harvestDate", "label": "采收日期"}, {"prop": "traceCode", "label": "溯源码"}, {"prop": "chainStatus", "label": "上链状态"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "batchNo", "label": "批次编号"}, {"prop": "productName", "label": "产品名称"}, {"prop": "baseName", "label": "基地"}, {"prop": "farmerName", "label": "农户"}, {"prop": "harvestDate", "label": "采收日期"}, {"prop": "traceCode", "label": "溯源码"}, {"prop": "chainStatus", "label": "上链状态", "type": "select", "options": [{"label": "WAIT_CHAIN", "value": "WAIT_CHAIN"}, {"label": "CHAINED", "value": "CHAINED"}, {"label": "FAILED", "value": "FAILED"}]}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "PLANTED", "value": "PLANTED"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "CHAINED", "value": "CHAINED"}, {"label": "CLOSED", "value": "CLOSED"}]}]
const rowActions = [{"command": "submit", "label": "提交", "type": "primary"}, {"command": "chain", "label": "上链", "type": "primary"}, {"command": "close", "label": "完结", "type": "primary"}]
const defaults = {"chainStatus": "WAIT_CHAIN", "status": "PLANTED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitProductBatch(row.id)
  if (command === 'chain') await chainProductBatch(row.id)
  if (command === 'close') await closeProductBatch(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>

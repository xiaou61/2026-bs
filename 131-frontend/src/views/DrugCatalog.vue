<template>
  <DataPage title="药品目录" description="药品编号、名称、剂型、生产企业和批准文号维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getDrugCatalogPage, addDrugCatalog, updateDrugCatalog, deleteDrugCatalog, activateDrugCatalog, finishDrugCatalog } from '../api'
const api = { page: getDrugCatalogPage, add: addDrugCatalog, update: updateDrugCatalog, delete: deleteDrugCatalog }
const columns = [{"prop": "drugNo", "label": "药品编号"}, {"prop": "drugName", "label": "药品名称"}, {"prop": "dosageForm", "label": "剂型"}, {"prop": "manufacturerName", "label": "生产企业"}, {"prop": "approvalNo", "label": "批准文号"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "drugNo", "label": "药品编号"}, {"prop": "drugName", "label": "药品名称"}, {"prop": "dosageForm", "label": "剂型"}, {"prop": "manufacturerName", "label": "生产企业"}, {"prop": "approvalNo", "label": "批准文号"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}]}]
const rowActions = [{"command": "activate", "label": "启用", "type": "success"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateDrugCatalog(row.id)
  if (command === 'finish') await finishDrugCatalog(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>

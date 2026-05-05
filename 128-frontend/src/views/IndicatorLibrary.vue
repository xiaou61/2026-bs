<template>
  <DataPage title="指标库" description="ESG 指标编号、名称、维度、口径、权重和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getIndicatorLibraryPage, addIndicatorLibrary, updateIndicatorLibrary, deleteIndicatorLibrary, activateIndicatorLibrary, finishIndicatorLibrary } from '../api'
const api = { page: getIndicatorLibraryPage, add: addIndicatorLibrary, update: updateIndicatorLibrary, delete: deleteIndicatorLibrary }
const columns = [{"prop": "indicatorNo", "label": "指标编号"}, {"prop": "indicatorName", "label": "指标名称"}, {"prop": "dimensionName", "label": "ESG维度"}, {"prop": "caliberText", "label": "填报口径"}, {"prop": "weightValue", "label": "权重"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "indicatorNo", "label": "指标编号"}, {"prop": "indicatorName", "label": "指标名称"}, {"prop": "dimensionName", "label": "ESG维度"}, {"prop": "caliberText", "label": "填报口径"}, {"prop": "weightValue", "label": "权重", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "REJECTED", "value": "REJECTED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}]}]
const rowActions = [{"command": "activate", "label": "启用", "type": "success"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateIndicatorLibrary(row.id)
  if (command === 'finish') await finishIndicatorLibrary(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>

<template>
  <DataPage title="优化建议" description="资源优化建议、预计节省和处理状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getOptimizationAdvicePage, addOptimizationAdvice, updateOptimizationAdvice, deleteOptimizationAdvice, acceptOptimizationAdvice, finishOptimizationAdvice, rejectOptimizationAdvice } from '../api'
const api = { page: getOptimizationAdvicePage, add: addOptimizationAdvice, update: updateOptimizationAdvice, delete: deleteOptimizationAdvice }
const columns = [{"prop": "adviceNo", "label": "建议编号"}, {"prop": "resourceName", "label": "资源名称"}, {"prop": "adviceType", "label": "建议类型"}, {"prop": "expectedSaving", "label": "预计节省"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "generatedTime", "label": "生成时间"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "adviceNo", "label": "建议编号"}, {"prop": "resourceName", "label": "资源名称"}, {"prop": "adviceType", "label": "建议类型", "type": "select", "options": [{"label": "DOWNSIZE", "value": "DOWNSIZE"}, {"label": "CLEAN", "value": "CLEAN"}, {"label": "BUY_PLAN", "value": "BUY_PLAN"}, {"label": "MERGE", "value": "MERGE"}]}, {"prop": "expectedSaving", "label": "预计节省", "type": "number"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "generatedTime", "label": "生成时间"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "GENERATED", "value": "GENERATED"}, {"label": "ACCEPTED", "value": "ACCEPTED"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "REJECTED", "value": "REJECTED"}]}]
const rowActions = [{"command": "accept", "label": "采纳", "type": "primary"}, {"command": "finish", "label": "完成", "type": "primary"}, {"command": "reject", "label": "拒绝", "type": "primary"}]
const defaults = {"adviceType": "DOWNSIZE", "status": "GENERATED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'accept') await acceptOptimizationAdvice(row.id)
  if (command === 'finish') await finishOptimizationAdvice(row.id)
  if (command === 'reject') await rejectOptimizationAdvice(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>

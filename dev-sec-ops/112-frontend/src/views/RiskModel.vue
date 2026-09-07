<template>
  <DataPage title="风险模型" description="设备健康、身份可信和访问环境风险模型管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getRiskModelPage, addRiskModel, updateRiskModel, deleteRiskModel, publishRiskModel, archiveRiskModel } from '../api'
const api = { page: getRiskModelPage, add: addRiskModel, update: updateRiskModel, delete: deleteRiskModel }
const columns = [{"prop": "modelName", "label": "模型名称"}, {"prop": "modelCode", "label": "模型编码"}, {"prop": "modelType", "label": "模型类型"}, {"prop": "thresholdScore", "label": "阈值分"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "modelName", "label": "模型名称"}, {"prop": "modelCode", "label": "模型编码"}, {"prop": "modelType", "label": "模型类型", "type": "select", "options": [{"label": "DEVICE", "value": "DEVICE"}, {"label": "IDENTITY", "value": "IDENTITY"}, {"label": "LOCATION", "value": "LOCATION"}, {"label": "BEHAVIOR", "value": "BEHAVIOR"}]}, {"prop": "thresholdScore", "label": "阈值分", "type": "number"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "DRAFT", "value": "DRAFT"}, {"label": "ACTIVE", "value": "ACTIVE"}, {"label": "ARCHIVED", "value": "ARCHIVED"}]}]
const rowActions = [{"command": "publish", "label": "发布", "type": "primary"}, {"command": "archive", "label": "归档", "type": "primary"}]
const defaults = {"modelType": "DEVICE", "status": "DRAFT"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'publish') await publishRiskModel(row.id)
  if (command === 'archive') await archiveRiskModel(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>

<template>
  <DataPage title="寿命预测" description="备件寿命预测、剩余小时、健康分、风险等级、模型版本和预测状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getLifePredictionPage, addLifePrediction, updateLifePrediction, deleteLifePrediction, confirmLifePrediction, archiveLifePrediction } from '../api'
const api = { page: getLifePredictionPage, add: addLifePrediction, update: updateLifePrediction, delete: deleteLifePrediction }
const columns = [{"prop": "predictionNo", "label": "预测编号"}, {"prop": "partName", "label": "备件名称"}, {"prop": "equipmentName", "label": "设备名称"}, {"prop": "remainHours", "label": "剩余小时"}, {"prop": "healthScore", "label": "健康分"}, {"prop": "riskLevel", "label": "风险等级"}, {"prop": "modelVersion", "label": "模型版本"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "predictionNo", "label": "预测编号"}, {"prop": "partName", "label": "备件名称"}, {"prop": "equipmentName", "label": "设备名称"}, {"prop": "remainHours", "label": "剩余小时", "type": "number"}, {"prop": "healthScore", "label": "健康分", "type": "number"}, {"prop": "riskLevel", "label": "风险等级", "type": "select", "options": [{"label": "高", "value": "高"}, {"label": "中", "value": "中"}, {"label": "低", "value": "低"}]}, {"prop": "modelVersion", "label": "模型版本"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "GENERATED", "value": "GENERATED"}, {"label": "CONFIRMED", "value": "CONFIRMED"}, {"label": "ARCHIVED", "value": "ARCHIVED"}]}]
const rowActions = [{"command": "confirm", "label": "确认", "type": "success"}, {"command": "archive", "label": "归档", "type": "warning"}]
const defaults = {"status": "GENERATED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'confirm') await confirmLifePrediction(row.id)
  if (command === 'archive') await archiveLifePrediction(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>

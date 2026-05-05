<template>
  <DataPage title="空位预测" description="预测编号、停车场、预测时段、预测空位、置信度和模型版本管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getVacancyPredictionPage, addVacancyPrediction, updateVacancyPrediction, deleteVacancyPrediction, publishVacancyPrediction, closeVacancyPrediction } from '../api'
const api = { page: getVacancyPredictionPage, add: addVacancyPrediction, update: updateVacancyPrediction, delete: deleteVacancyPrediction }
const columns = [{"prop": "predictionNo", "label": "预测编号"}, {"prop": "lotName", "label": "停车场"}, {"prop": "predictTime", "label": "预测时段"}, {"prop": "vacancyCount", "label": "预测空位"}, {"prop": "confidenceScore", "label": "置信度"}, {"prop": "modelVersion", "label": "模型版本"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "predictionNo", "label": "预测编号"}, {"prop": "lotName", "label": "停车场"}, {"prop": "predictTime", "label": "预测时段"}, {"prop": "vacancyCount", "label": "预测空位", "type": "number"}, {"prop": "confidenceScore", "label": "置信度", "type": "number"}, {"prop": "modelVersion", "label": "模型版本"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "AVAILABLE", "value": "AVAILABLE"}, {"label": "OCCUPIED", "value": "OCCUPIED"}, {"label": "RESERVED", "value": "RESERVED"}, {"label": "ENTERED", "value": "ENTERED"}, {"label": "LEFT", "value": "LEFT"}, {"label": "PAID", "value": "PAID"}, {"label": "REFUNDED", "value": "REFUNDED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}]}]
const rowActions = [{"command": "publish", "label": "发布", "type": "success"}, {"command": "close", "label": "关闭", "type": "info"}]
const defaults = {"status": "PUBLISHED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'publish') await publishVacancyPrediction(row.id)
  if (command === 'close') await closeVacancyPrediction(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>

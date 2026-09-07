<template>
  <DataPage title="评分模型" description="评分模型、维度、评分方法、权重和启用状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getScoreModelPage, addScoreModel, updateScoreModel, deleteScoreModel, activateScoreModel, finishScoreModel } from '../api'
const api = { page: getScoreModelPage, add: addScoreModel, update: updateScoreModel, delete: deleteScoreModel }
const columns = [{"prop": "modelNo", "label": "模型编号"}, {"prop": "modelName", "label": "模型名称"}, {"prop": "dimensionName", "label": "ESG维度"}, {"prop": "scoreMethod", "label": "评分方法"}, {"prop": "weightValue", "label": "权重"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "modelNo", "label": "模型编号"}, {"prop": "modelName", "label": "模型名称"}, {"prop": "dimensionName", "label": "ESG维度"}, {"prop": "scoreMethod", "label": "评分方法"}, {"prop": "weightValue", "label": "权重", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "REJECTED", "value": "REJECTED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}]}]
const rowActions = [{"command": "activate", "label": "启用", "type": "success"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateScoreModel(row.id)
  if (command === 'finish') await finishScoreModel(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>

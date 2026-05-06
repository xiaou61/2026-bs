<template>
  <DataPage title="任务书下达" description="任务书编号、课题编号、阶段任务、下达时间、指导教师和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getMaterialReviewPage, addMaterialReview, updateMaterialReview, deleteMaterialReview, processMaterialReview, finishMaterialReview } from '../api'
const api = { page: getMaterialReviewPage, add: addMaterialReview, update: updateMaterialReview, delete: deleteMaterialReview }
const columns = [{"prop": "paymentNo", "label": "任务书编号"}, {"prop": "claimNo", "label": "课题编号"}, {"prop": "paymentAmount", "label": "阶段任务"}, {"prop": "paymentTime", "label": "下达时间"}, {"prop": "operatorName", "label": "指导教师"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "paymentNo", "label": "任务书编号"}, {"prop": "claimNo", "label": "课题编号"}, {"prop": "paymentAmount", "label": "阶段任务", "type": "number"}, {"prop": "paymentTime", "label": "下达时间"}, {"prop": "operatorName", "label": "指导教师"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "SUCCESS", "value": "SUCCESS"}]}]
const rowActions = [{"command": "process", "label": "处理", "type": "warning"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "FINISHED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processMaterialReview(row.id)
  if (command === 'finish') await finishMaterialReview(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>






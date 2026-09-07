<template>
  <DataPage title="施肥计划" description="施肥计划、温室、肥料类型、计划用量和负责人维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getFertilizerPlanPage, addFertilizerPlan, updateFertilizerPlan, deleteFertilizerPlan, activateFertilizerPlan, finishFertilizerPlan } from '../api'
const api = { page: getFertilizerPlanPage, add: addFertilizerPlan, update: updateFertilizerPlan, delete: deleteFertilizerPlan }
const columns = [{"prop": "planNo", "label": "计划编号"}, {"prop": "greenhouseNo", "label": "温室编号"}, {"prop": "fertilizerType", "label": "肥料类型"}, {"prop": "planAmount", "label": "计划用量"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "planNo", "label": "计划编号"}, {"prop": "greenhouseNo", "label": "温室编号"}, {"prop": "fertilizerType", "label": "肥料类型"}, {"prop": "planAmount", "label": "计划用量", "type": "number"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}]}]
const rowActions = [{"command": "activate", "label": "启用", "type": "success"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateFertilizerPlan(row.id)
  if (command === 'finish') await finishFertilizerPlan(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>

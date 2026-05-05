<template>
  <DataPage title="投喂计划" description="计划编号、池塘、鱼种、饲料类型和日投喂量维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getFeedingPlanPage, addFeedingPlan, updateFeedingPlan, deleteFeedingPlan, activateFeedingPlan, finishFeedingPlan } from '../api'
const api = { page: getFeedingPlanPage, add: addFeedingPlan, update: updateFeedingPlan, delete: deleteFeedingPlan }
const columns = [{"prop": "planNo", "label": "计划编号"}, {"prop": "pondNo", "label": "池塘编号"}, {"prop": "fishSpecies", "label": "鱼种"}, {"prop": "feedType", "label": "饲料类型"}, {"prop": "dailyAmount", "label": "日投喂量"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "planNo", "label": "计划编号"}, {"prop": "pondNo", "label": "池塘编号"}, {"prop": "fishSpecies", "label": "鱼种"}, {"prop": "feedType", "label": "饲料类型"}, {"prop": "dailyAmount", "label": "日投喂量", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}]}]
const rowActions = [{"command": "activate", "label": "启用", "type": "success"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateFeedingPlan(row.id)
  if (command === 'finish') await finishFeedingPlan(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>

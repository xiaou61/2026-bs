<template>
  <DataPage title="安全培训" description="培训编号、课程、讲师、班组、人数和培训日期管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getSafetyTrainingPage, addSafetyTraining, updateSafetyTraining, deleteSafetyTraining, publishSafetyTraining, finishSafetyTraining } from '../api'
const api = { page: getSafetyTrainingPage, add: addSafetyTraining, update: updateSafetyTraining, delete: deleteSafetyTraining }
const columns = [{"prop": "trainingNo", "label": "培训编号"}, {"prop": "courseName", "label": "课程名称"}, {"prop": "trainerName", "label": "讲师"}, {"prop": "teamName", "label": "班组"}, {"prop": "traineeCount", "label": "参训人数"}, {"prop": "trainDate", "label": "培训日期"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "trainingNo", "label": "培训编号"}, {"prop": "courseName", "label": "课程名称"}, {"prop": "trainerName", "label": "讲师"}, {"prop": "teamName", "label": "班组"}, {"prop": "traineeCount", "label": "参训人数", "type": "number"}, {"prop": "trainDate", "label": "培训日期"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "CREATED", "value": "CREATED"}, {"label": "ASSIGNED", "value": "ASSIGNED"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "CLOSED", "value": "CLOSED"}, {"label": "PASS", "value": "PASS"}, {"label": "FAIL", "value": "FAIL"}, {"label": "WARNING", "value": "WARNING"}]}]
const rowActions = [{"command": "publish", "label": "发布", "type": "primary"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "CREATED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'publish') await publishSafetyTraining(row.id)
  if (command === 'finish') await finishSafetyTraining(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>

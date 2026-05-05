<template>
  <DataPage title="环境读数" description="读数编号、温室、采集时间、温度、湿度和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getEnvironmentReadingPage, addEnvironmentReading, updateEnvironmentReading, deleteEnvironmentReading, processEnvironmentReading, finishEnvironmentReading } from '../api'
const api = { page: getEnvironmentReadingPage, add: addEnvironmentReading, update: updateEnvironmentReading, delete: deleteEnvironmentReading }
const columns = [{"prop": "readingNo", "label": "读数编号"}, {"prop": "greenhouseNo", "label": "温室编号"}, {"prop": "collectTime", "label": "采集时间"}, {"prop": "temperatureValue", "label": "温度"}, {"prop": "humidityValue", "label": "湿度"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "readingNo", "label": "读数编号"}, {"prop": "greenhouseNo", "label": "温室编号"}, {"prop": "collectTime", "label": "采集时间"}, {"prop": "temperatureValue", "label": "温度", "type": "number"}, {"prop": "humidityValue", "label": "湿度", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}]}]
const rowActions = [{"command": "process", "label": "处理", "type": "primary"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "NORMAL"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processEnvironmentReading(row.id)
  if (command === 'finish') await finishEnvironmentReading(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>

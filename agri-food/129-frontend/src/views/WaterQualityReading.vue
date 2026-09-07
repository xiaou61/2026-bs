<template>
  <DataPage title="水质读数" description="读数编号、池塘、采集时间、溶氧、PH和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getWaterQualityReadingPage, addWaterQualityReading, updateWaterQualityReading, deleteWaterQualityReading, processWaterQualityReading, finishWaterQualityReading } from '../api'
const api = { page: getWaterQualityReadingPage, add: addWaterQualityReading, update: updateWaterQualityReading, delete: deleteWaterQualityReading }
const columns = [{"prop": "readingNo", "label": "读数编号"}, {"prop": "pondNo", "label": "池塘编号"}, {"prop": "collectTime", "label": "采集时间"}, {"prop": "dissolvedOxygen", "label": "溶氧"}, {"prop": "phValue", "label": "PH值"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "readingNo", "label": "读数编号"}, {"prop": "pondNo", "label": "池塘编号"}, {"prop": "collectTime", "label": "采集时间"}, {"prop": "dissolvedOxygen", "label": "溶氧", "type": "number"}, {"prop": "phValue", "label": "PH值", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}]}]
const rowActions = [{"command": "process", "label": "处理", "type": "primary"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "NORMAL"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processWaterQualityReading(row.id)
  if (command === 'finish') await finishWaterQualityReading(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>

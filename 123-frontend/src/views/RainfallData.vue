<template>
  <DataPage title="雨量数据" description="数据编号、雨量站、采集时间、小时雨量、累计雨量和雨强等级管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getRainfallDataPage, addRainfallData, updateRainfallData, deleteRainfallData, warnRainfallData, recoverRainfallData } from '../api'
const api = { page: getRainfallDataPage, add: addRainfallData, update: updateRainfallData, delete: deleteRainfallData }
const columns = [{"prop": "dataNo", "label": "数据编号"}, {"prop": "stationName", "label": "雨量站"}, {"prop": "collectTime", "label": "采集时间"}, {"prop": "hourRainfall", "label": "小时雨量"}, {"prop": "totalRainfall", "label": "累计雨量"}, {"prop": "rainLevel", "label": "雨强等级"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "dataNo", "label": "数据编号"}, {"prop": "stationName", "label": "雨量站"}, {"prop": "collectTime", "label": "采集时间"}, {"prop": "hourRainfall", "label": "小时雨量", "type": "number"}, {"prop": "totalRainfall", "label": "累计雨量", "type": "number"}, {"prop": "rainLevel", "label": "雨强等级", "type": "select", "options": [{"label": "小雨", "value": "小雨"}, {"label": "中雨", "value": "中雨"}, {"label": "大雨", "value": "大雨"}, {"label": "暴雨", "value": "暴雨"}, {"label": "大暴雨", "value": "大暴雨"}]}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "WARNING", "value": "WARNING"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "ASSIGNED", "value": "ASSIGNED"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "CLOSED", "value": "CLOSED"}, {"label": "PUBLISHED", "value": "PUBLISHED"}]}]
const rowActions = [{"command": "warn", "label": "预警", "type": "warning"}, {"command": "recover", "label": "恢复", "type": "success"}]
const defaults = {"status": "NORMAL"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'warn') await warnRainfallData(row.id)
  if (command === 'recover') await recoverRainfallData(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>

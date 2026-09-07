<template>
  <DataPage title="水位数据" description="数据编号、点位、采集时间、水位值、上涨速度和数据状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getWaterLevelDataPage, addWaterLevelData, updateWaterLevelData, deleteWaterLevelData, warnWaterLevelData, recoverWaterLevelData } from '../api'
const api = { page: getWaterLevelDataPage, add: addWaterLevelData, update: updateWaterLevelData, delete: deleteWaterLevelData }
const columns = [{"prop": "dataNo", "label": "数据编号"}, {"prop": "pointName", "label": "点位名称"}, {"prop": "collectTime", "label": "采集时间"}, {"prop": "waterLevel", "label": "水位值"}, {"prop": "riseSpeed", "label": "上涨速度"}, {"prop": "sourceType", "label": "来源"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "dataNo", "label": "数据编号"}, {"prop": "pointName", "label": "点位名称"}, {"prop": "collectTime", "label": "采集时间"}, {"prop": "waterLevel", "label": "水位值", "type": "number"}, {"prop": "riseSpeed", "label": "上涨速度", "type": "number"}, {"prop": "sourceType", "label": "来源"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "WARNING", "value": "WARNING"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "ASSIGNED", "value": "ASSIGNED"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "CLOSED", "value": "CLOSED"}, {"label": "PUBLISHED", "value": "PUBLISHED"}]}]
const rowActions = [{"command": "warn", "label": "预警", "type": "warning"}, {"command": "recover", "label": "恢复", "type": "success"}]
const defaults = {"status": "NORMAL"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'warn') await warnWaterLevelData(row.id)
  if (command === 'recover') await recoverWaterLevelData(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>

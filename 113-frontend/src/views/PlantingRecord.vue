<template>
  <DataPage title="种植记录" description="播种、施肥、灌溉、采收等生产过程记录管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getPlantingRecordPage, addPlantingRecord, updatePlantingRecord, deletePlantingRecord, confirmPlantingRecord, chainPlantingRecord } from '../api'
const api = { page: getPlantingRecordPage, add: addPlantingRecord, update: updatePlantingRecord, delete: deletePlantingRecord }
const columns = [{"prop": "recordNo", "label": "记录编号"}, {"prop": "batchNo", "label": "批次编号"}, {"prop": "operationType", "label": "操作类型"}, {"prop": "operationDate", "label": "操作日期"}, {"prop": "operatorName", "label": "操作人"}, {"prop": "detailText", "label": "记录详情"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "recordNo", "label": "记录编号"}, {"prop": "batchNo", "label": "批次编号"}, {"prop": "operationType", "label": "操作类型", "type": "select", "options": [{"label": "SOWING", "value": "SOWING"}, {"label": "FERTILIZE", "value": "FERTILIZE"}, {"label": "IRRIGATE", "value": "IRRIGATE"}, {"label": "HARVEST", "value": "HARVEST"}]}, {"prop": "operationDate", "label": "操作日期"}, {"prop": "operatorName", "label": "操作人"}, {"prop": "detailText", "label": "记录详情", "type": "textarea"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "RECORDED", "value": "RECORDED"}, {"label": "CONFIRMED", "value": "CONFIRMED"}, {"label": "CHAINED", "value": "CHAINED"}]}]
const rowActions = [{"command": "confirm", "label": "确认", "type": "primary"}, {"command": "chain", "label": "上链", "type": "primary"}]
const defaults = {"status": "RECORDED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'confirm') await confirmPlantingRecord(row.id)
  if (command === 'chain') await chainPlantingRecord(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>

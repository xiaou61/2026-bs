<template>
  <DataPage title="停车记录" description="停车编号、预约单、车牌、入场时间、离场时间和停车时长管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getParkingRecordPage, addParkingRecord, updateParkingRecord, deleteParkingRecord, leaveParkingRecord, closeParkingRecord } from '../api'
const api = { page: getParkingRecordPage, add: addParkingRecord, update: updateParkingRecord, delete: deleteParkingRecord }
const columns = [{"prop": "recordNo", "label": "停车编号"}, {"prop": "reservationNo", "label": "预约单号"}, {"prop": "plateNo", "label": "车牌"}, {"prop": "enterTime", "label": "入场时间"}, {"prop": "leaveTime", "label": "离场时间"}, {"prop": "parkingMinute", "label": "停车分钟"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "recordNo", "label": "停车编号"}, {"prop": "reservationNo", "label": "预约单号"}, {"prop": "plateNo", "label": "车牌"}, {"prop": "enterTime", "label": "入场时间"}, {"prop": "leaveTime", "label": "离场时间"}, {"prop": "parkingMinute", "label": "停车分钟", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "AVAILABLE", "value": "AVAILABLE"}, {"label": "OCCUPIED", "value": "OCCUPIED"}, {"label": "RESERVED", "value": "RESERVED"}, {"label": "ENTERED", "value": "ENTERED"}, {"label": "LEFT", "value": "LEFT"}, {"label": "PAID", "value": "PAID"}, {"label": "REFUNDED", "value": "REFUNDED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}]}]
const rowActions = [{"command": "leave", "label": "离场", "type": "success"}, {"command": "close", "label": "关闭", "type": "info"}]
const defaults = {"status": "ENTERED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'leave') await leaveParkingRecord(row.id)
  if (command === 'close') await closeParkingRecord(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>

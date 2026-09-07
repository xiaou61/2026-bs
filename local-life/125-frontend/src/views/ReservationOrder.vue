<template>
  <DataPage title="预约订单" description="预约单号、车主、停车场、车位、预约时间和预计时长管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getReservationOrderPage, addReservationOrder, updateReservationOrder, deleteReservationOrder, enterReservationOrder, finishReservationOrder } from '../api'
const api = { page: getReservationOrderPage, add: addReservationOrder, update: updateReservationOrder, delete: deleteReservationOrder }
const columns = [{"prop": "reservationNo", "label": "预约单号"}, {"prop": "ownerName", "label": "车主"}, {"prop": "lotName", "label": "停车场"}, {"prop": "spaceNo", "label": "车位编号"}, {"prop": "reservationTime", "label": "预约时间"}, {"prop": "durationMinute", "label": "预计分钟"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "reservationNo", "label": "预约单号"}, {"prop": "ownerName", "label": "车主"}, {"prop": "lotName", "label": "停车场"}, {"prop": "spaceNo", "label": "车位编号"}, {"prop": "reservationTime", "label": "预约时间"}, {"prop": "durationMinute", "label": "预计分钟", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "AVAILABLE", "value": "AVAILABLE"}, {"label": "OCCUPIED", "value": "OCCUPIED"}, {"label": "RESERVED", "value": "RESERVED"}, {"label": "ENTERED", "value": "ENTERED"}, {"label": "LEFT", "value": "LEFT"}, {"label": "PAID", "value": "PAID"}, {"label": "REFUNDED", "value": "REFUNDED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}]}]
const rowActions = [{"command": "enter", "label": "入场", "type": "primary"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "RESERVED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'enter') await enterReservationOrder(row.id)
  if (command === 'finish') await finishReservationOrder(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>

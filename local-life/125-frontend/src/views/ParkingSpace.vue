<template>
  <DataPage title="车位档案" description="车位编号、所属区域、车位类型、车位编号牌、收费类型和状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getParkingSpacePage, addParkingSpace, updateParkingSpace, deleteParkingSpace, availableParkingSpace, occupyParkingSpace } from '../api'
const api = { page: getParkingSpacePage, add: addParkingSpace, update: updateParkingSpace, delete: deleteParkingSpace }
const columns = [{"prop": "spaceNo", "label": "车位编号"}, {"prop": "areaName", "label": "区域"}, {"prop": "spaceType", "label": "车位类型"}, {"prop": "plateLabel", "label": "编号牌"}, {"prop": "feeType", "label": "收费类型"}, {"prop": "sensorNo", "label": "传感器编号"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "spaceNo", "label": "车位编号"}, {"prop": "areaName", "label": "区域"}, {"prop": "spaceType", "label": "车位类型", "type": "select", "options": [{"label": "标准车位", "value": "标准车位"}, {"label": "新能源车位", "value": "新能源车位"}, {"label": "无障碍车位", "value": "无障碍车位"}]}, {"prop": "plateLabel", "label": "编号牌"}, {"prop": "feeType", "label": "收费类型", "type": "select", "options": [{"label": "计时", "value": "计时"}, {"label": "包月", "value": "包月"}]}, {"prop": "sensorNo", "label": "传感器编号"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "AVAILABLE", "value": "AVAILABLE"}, {"label": "OCCUPIED", "value": "OCCUPIED"}, {"label": "RESERVED", "value": "RESERVED"}, {"label": "ENTERED", "value": "ENTERED"}, {"label": "LEFT", "value": "LEFT"}, {"label": "PAID", "value": "PAID"}, {"label": "REFUNDED", "value": "REFUNDED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}]}]
const rowActions = [{"command": "available", "label": "空闲", "type": "success"}, {"command": "occupy", "label": "占用", "type": "warning"}]
const defaults = {"status": "AVAILABLE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'available') await availableParkingSpace(row.id)
  if (command === 'occupy') await occupyParkingSpace(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>

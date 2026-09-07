<template>
  <DataPage title="库位档案" description="库位编码、所属区域、承重、体积、热度等级和占用状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getStorageLocationPage, addStorageLocation, updateStorageLocation, deleteStorageLocation, reserveStorageLocation, releaseStorageLocation, lockStorageLocation } from '../api'
const api = { page: getStorageLocationPage, add: addStorageLocation, update: updateStorageLocation, delete: deleteStorageLocation }
const columns = [{"prop": "locationNo", "label": "库位编码"}, {"prop": "zoneName", "label": "所属区域"}, {"prop": "locationType", "label": "库位类型"}, {"prop": "maxWeight", "label": "最大承重"}, {"prop": "volumeCapacity", "label": "体积容量"}, {"prop": "heatLevel", "label": "热度等级"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "locationNo", "label": "库位编码"}, {"prop": "zoneName", "label": "所属区域"}, {"prop": "locationType", "label": "库位类型", "type": "select", "options": [{"label": "托盘位", "value": "托盘位"}, {"label": "料箱位", "value": "料箱位"}, {"label": "冷链位", "value": "冷链位"}, {"label": "重货位", "value": "重货位"}]}, {"prop": "maxWeight", "label": "最大承重", "type": "number"}, {"prop": "volumeCapacity", "label": "体积容量", "type": "number"}, {"prop": "heatLevel", "label": "热度等级", "type": "select", "options": [{"label": "高频", "value": "高频"}, {"label": "中频", "value": "中频"}, {"label": "低频", "value": "低频"}]}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "EMPTY", "value": "EMPTY"}, {"label": "OCCUPIED", "value": "OCCUPIED"}, {"label": "RESERVED", "value": "RESERVED"}, {"label": "LOCKED", "value": "LOCKED"}]}]
const rowActions = [{"command": "reserve", "label": "预占", "type": "warning"}, {"command": "release", "label": "释放", "type": "success"}, {"command": "lock", "label": "锁定", "type": "danger"}]
const defaults = {"status": "EMPTY"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'reserve') await reserveStorageLocation(row.id)
  if (command === 'release') await releaseStorageLocation(row.id)
  if (command === 'lock') await lockStorageLocation(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>

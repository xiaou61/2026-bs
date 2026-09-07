<template>
  <DataPage title="巡检点位" description="点位编号、点位名称、设备、楼层、检查标准和点位状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getInspectionPointPage, addInspectionPoint, updateInspectionPoint, deleteInspectionPoint, lockInspectionPoint, releaseInspectionPoint } from '../api'
const api = { page: getInspectionPointPage, add: addInspectionPoint, update: updateInspectionPoint, delete: deleteInspectionPoint }
const columns = [{"prop": "pointNo", "label": "点位编号"}, {"prop": "pointName", "label": "点位名称"}, {"prop": "deviceName", "label": "关联设备"}, {"prop": "floorName", "label": "楼层"}, {"prop": "checkStandard", "label": "检查标准"}, {"prop": "lastResult", "label": "上次结果"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "pointNo", "label": "点位编号"}, {"prop": "pointName", "label": "点位名称"}, {"prop": "deviceName", "label": "关联设备"}, {"prop": "floorName", "label": "楼层"}, {"prop": "checkStandard", "label": "检查标准"}, {"prop": "lastResult", "label": "上次结果", "type": "select", "options": [{"label": "正常", "value": "正常"}, {"label": "异常", "value": "异常"}, {"label": "待复核", "value": "待复核"}]}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "NORMAL", "value": "NORMAL"}, {"label": "LOCKED", "value": "LOCKED"}]}]
const rowActions = [{"command": "lock", "label": "锁定", "type": "warning"}, {"command": "release", "label": "释放", "type": "success"}]
const defaults = {"status": "NORMAL"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'lock') await lockInspectionPoint(row.id)
  if (command === 'release') await releaseInspectionPoint(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>

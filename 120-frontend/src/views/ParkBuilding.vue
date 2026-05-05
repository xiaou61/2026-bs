<template>
  <DataPage title="园区楼宇" description="园区楼宇、楼层数量、面积、负责人、孪生状态和运行状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getParkBuildingPage, addParkBuilding, updateParkBuilding, deleteParkBuilding, enableParkBuilding, disableParkBuilding } from '../api'
const api = { page: getParkBuildingPage, add: addParkBuilding, update: updateParkBuilding, delete: deleteParkBuilding }
const columns = [{"prop": "buildingNo", "label": "楼宇编号"}, {"prop": "buildingName", "label": "楼宇名称"}, {"prop": "floorCount", "label": "楼层数"}, {"prop": "areaSize", "label": "面积"}, {"prop": "managerName", "label": "负责人"}, {"prop": "twinStatus", "label": "孪生状态"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "buildingNo", "label": "楼宇编号"}, {"prop": "buildingName", "label": "楼宇名称"}, {"prop": "floorCount", "label": "楼层数", "type": "number"}, {"prop": "areaSize", "label": "面积", "type": "number"}, {"prop": "managerName", "label": "负责人"}, {"prop": "twinStatus", "label": "孪生状态", "type": "select", "options": [{"label": "已建模", "value": "已建模"}, {"label": "建模中", "value": "建模中"}, {"label": "待建模", "value": "待建模"}]}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}]}]
const rowActions = [{"command": "enable", "label": "启用", "type": "success"}, {"command": "disable", "label": "停用", "type": "danger"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'enable') await enableParkBuilding(row.id)
  if (command === 'disable') await disableParkBuilding(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>

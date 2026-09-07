<template>
  <DataPage title="车主车辆" description="车辆编号、车主、车牌、车型、手机号和会员等级管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getVehicleProfilePage, addVehicleProfile, updateVehicleProfile, deleteVehicleProfile, enableVehicleProfile, disableVehicleProfile } from '../api'
const api = { page: getVehicleProfilePage, add: addVehicleProfile, update: updateVehicleProfile, delete: deleteVehicleProfile }
const columns = [{"prop": "vehicleNo", "label": "车辆编号"}, {"prop": "ownerName", "label": "车主"}, {"prop": "plateNo", "label": "车牌"}, {"prop": "vehicleType", "label": "车型"}, {"prop": "phone", "label": "手机号"}, {"prop": "memberLevel", "label": "会员等级"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "vehicleNo", "label": "车辆编号"}, {"prop": "ownerName", "label": "车主"}, {"prop": "plateNo", "label": "车牌"}, {"prop": "vehicleType", "label": "车型"}, {"prop": "phone", "label": "手机号"}, {"prop": "memberLevel", "label": "会员等级", "type": "select", "options": [{"label": "普通", "value": "普通"}, {"label": "银卡", "value": "银卡"}, {"label": "金卡", "value": "金卡"}]}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "AVAILABLE", "value": "AVAILABLE"}, {"label": "OCCUPIED", "value": "OCCUPIED"}, {"label": "RESERVED", "value": "RESERVED"}, {"label": "ENTERED", "value": "ENTERED"}, {"label": "LEFT", "value": "LEFT"}, {"label": "PAID", "value": "PAID"}, {"label": "REFUNDED", "value": "REFUNDED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}]}]
const rowActions = [{"command": "enable", "label": "启用", "type": "success"}, {"command": "disable", "label": "停用", "type": "danger"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'enable') await enableVehicleProfile(row.id)
  if (command === 'disable') await disableVehicleProfile(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>

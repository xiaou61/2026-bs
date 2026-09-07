<template><DataPage title="门禁设备" description="设备编号、设备名称、设备类型、设备管理员、巡检时间和设备状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'SECURITY', 'MANAGER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addGateDevice, deleteGateDevice, finishGateDevice, getGateDevicePage, processGateDevice, updateGateDevice } from '../api'
const api = { page: getGateDevicePage, add: addGateDevice, update: updateGateDevice, delete: deleteGateDevice, process: processGateDevice, finish: finishGateDevice }
const columns = [{ prop: 'recordNo', label: '设备编号' }, { prop: 'recordName', label: '设备名称' }, { prop: 'category', label: '设备类型' }, { prop: 'ownerName', label: '设备管理员' }, { prop: 'planTime', label: '巡检时间' }, { prop: 'status', label: '设备状态' }, { prop: 'remark', label: '设备说明' }]
const formFields = [{ prop: 'recordNo', label: '设备编号' }, { prop: 'recordName', label: '设备名称' }, { prop: 'category', label: '设备类型' }, { prop: 'ownerName', label: '设备管理员' }, { prop: 'planTime', label: '巡检时间' }, { prop: 'status', label: '设备状态', type: 'select' }, { prop: 'remark', label: '设备说明', type: 'textarea' }]
</script>

<template><DataPage title="车辆绑定" description="绑定编号、车主车辆、绑定类型、管理人员、绑定时间和绑定状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'PARKING', 'PATROL', 'TENANT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addVehicleBind, deleteVehicleBind, finishVehicleBind, getVehicleBindPage, processVehicleBind, updateVehicleBind } from '../api'
const api = { page: getVehicleBindPage, add: addVehicleBind, update: updateVehicleBind, delete: deleteVehicleBind, process: processVehicleBind, finish: finishVehicleBind }
const columns = [{ prop: 'recordNo', label: '绑定编号' }, { prop: 'recordName', label: '车主车辆' }, { prop: 'category', label: '绑定类型' }, { prop: 'ownerName', label: '管理人员' }, { prop: 'planTime', label: '绑定时间' }, { prop: 'status', label: '绑定状态' }, { prop: 'remark', label: '绑定说明' }]
const formFields = [{ prop: 'recordNo', label: '绑定编号' }, { prop: 'recordName', label: '车主车辆' }, { prop: 'category', label: '绑定类型' }, { prop: 'ownerName', label: '管理人员' }, { prop: 'planTime', label: '绑定时间' }, { prop: 'status', label: '绑定状态', type: 'select' }, { prop: 'remark', label: '绑定说明', type: 'textarea' }]
</script>

<template><DataPage title="车辆调度" description="调度编号、转运联单、车辆类型、驾驶人员、调度时间和调度状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'PARK', 'TRANSPORT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addVehicleDispatch, deleteVehicleDispatch, finishVehicleDispatch, getVehicleDispatchPage, processVehicleDispatch, updateVehicleDispatch } from '../api'
const api = { page: getVehicleDispatchPage, add: addVehicleDispatch, update: updateVehicleDispatch, delete: deleteVehicleDispatch, process: processVehicleDispatch, finish: finishVehicleDispatch }
const columns = [{ prop: 'recordNo', label: '调度编号' }, { prop: 'recordName', label: '转运联单' }, { prop: 'category', label: '车辆类型' }, { prop: 'ownerName', label: '驾驶人员' }, { prop: 'planTime', label: '调度时间' }, { prop: 'status', label: '调度状态' }, { prop: 'remark', label: '调度说明' }]
const formFields = [{ prop: 'recordNo', label: '调度编号' }, { prop: 'recordName', label: '转运联单' }, { prop: 'category', label: '车辆类型' }, { prop: 'ownerName', label: '驾驶人员' }, { prop: 'planTime', label: '调度时间' }, { prop: 'status', label: '调度状态', type: 'select' }, { prop: 'remark', label: '调度说明', type: 'textarea' }]
</script>

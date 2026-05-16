<template><DataPage title="占位巡检" description="巡检编号、车位区域、巡检类型、巡检人员、巡检时间和巡检状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'PARKING', 'PATROL']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addOccupancyCheck, deleteOccupancyCheck, finishOccupancyCheck, getOccupancyCheckPage, processOccupancyCheck, updateOccupancyCheck } from '../api'
const api = { page: getOccupancyCheckPage, add: addOccupancyCheck, update: updateOccupancyCheck, delete: deleteOccupancyCheck, process: processOccupancyCheck, finish: finishOccupancyCheck }
const columns = [{ prop: 'recordNo', label: '巡检编号' }, { prop: 'recordName', label: '车位区域' }, { prop: 'category', label: '巡检类型' }, { prop: 'ownerName', label: '巡检人员' }, { prop: 'planTime', label: '巡检时间' }, { prop: 'status', label: '巡检状态' }, { prop: 'remark', label: '巡检说明' }]
const formFields = [{ prop: 'recordNo', label: '巡检编号' }, { prop: 'recordName', label: '车位区域' }, { prop: 'category', label: '巡检类型' }, { prop: 'ownerName', label: '巡检人员' }, { prop: 'planTime', label: '巡检时间' }, { prop: 'status', label: '巡检状态', type: 'select' }, { prop: 'remark', label: '巡检说明', type: 'textarea' }]
</script>

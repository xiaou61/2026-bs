<template><DataPage title="上门记录" description="记录编号、服务预约、服务类型、家政人员、上门时间和服务状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'AGENCY', 'DISPATCH', 'WORKER', 'RESIDENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addServiceRecord, deleteServiceRecord, finishServiceRecord, getServiceRecordPage, processServiceRecord, updateServiceRecord } from '../api'
const api = { page: getServiceRecordPage, add: addServiceRecord, update: updateServiceRecord, delete: deleteServiceRecord, process: processServiceRecord, finish: finishServiceRecord }
const columns = [{ prop: 'recordNo', label: '记录编号' }, { prop: 'recordName', label: '服务预约' }, { prop: 'category', label: '服务类型' }, { prop: 'ownerName', label: '家政人员' }, { prop: 'planTime', label: '上门时间' }, { prop: 'status', label: '服务状态' }, { prop: 'remark', label: '服务说明' }]
const formFields = [{ prop: 'recordNo', label: '记录编号' }, { prop: 'recordName', label: '服务预约' }, { prop: 'category', label: '服务类型' }, { prop: 'ownerName', label: '家政人员' }, { prop: 'planTime', label: '上门时间' }, { prop: 'status', label: '服务状态', type: 'select' }, { prop: 'remark', label: '服务说明', type: 'textarea' }]
</script>

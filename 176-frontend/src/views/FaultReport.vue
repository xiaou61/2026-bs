<template><DataPage title="故障上报" description="故障编号、故障阀门、故障类型、上报人员、上报时间和故障状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'DISPATCH', 'PATROL', 'REPAIR', 'SUPERVISOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addFaultReport, deleteFaultReport, finishFaultReport, getFaultReportPage, processFaultReport, updateFaultReport } from '../api'
const api = { page: getFaultReportPage, add: addFaultReport, update: updateFaultReport, delete: deleteFaultReport, process: processFaultReport, finish: finishFaultReport }
const columns = [{ prop: 'recordNo', label: '故障编号' }, { prop: 'recordName', label: '故障阀门' }, { prop: 'category', label: '故障类型' }, { prop: 'ownerName', label: '上报人员' }, { prop: 'planTime', label: '上报时间' }, { prop: 'status', label: '故障状态' }, { prop: 'remark', label: '故障说明' }]
const formFields = [{ prop: 'recordNo', label: '故障编号' }, { prop: 'recordName', label: '故障阀门' }, { prop: 'category', label: '故障类型' }, { prop: 'ownerName', label: '上报人员' }, { prop: 'planTime', label: '上报时间' }, { prop: 'status', label: '故障状态', type: 'select' }, { prop: 'remark', label: '故障说明', type: 'textarea' }]
</script>

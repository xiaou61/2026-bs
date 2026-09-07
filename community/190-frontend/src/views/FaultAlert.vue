<template><DataPage title="故障预警" description="预警编号、预警设备、预警类型、处置人员、预警时间和预警状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'PROPERTY', 'DISPATCH', 'TECHNICIAN', 'INSPECTOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addFaultAlert, deleteFaultAlert, finishFaultAlert, getFaultAlertPage, processFaultAlert, updateFaultAlert } from '../api'
const api = { page: getFaultAlertPage, add: addFaultAlert, update: updateFaultAlert, delete: deleteFaultAlert, process: processFaultAlert, finish: finishFaultAlert }
const columns = [{ prop: 'recordNo', label: '预警编号' }, { prop: 'recordName', label: '预警设备' }, { prop: 'category', label: '预警类型' }, { prop: 'ownerName', label: '处置人员' }, { prop: 'planTime', label: '预警时间' }, { prop: 'status', label: '预警状态' }, { prop: 'remark', label: '预警说明' }]
const formFields = [{ prop: 'recordNo', label: '预警编号' }, { prop: 'recordName', label: '预警设备' }, { prop: 'category', label: '预警类型' }, { prop: 'ownerName', label: '处置人员' }, { prop: 'planTime', label: '预警时间' }, { prop: 'status', label: '预警状态', type: 'select' }, { prop: 'remark', label: '预警说明', type: 'textarea' }]
</script>

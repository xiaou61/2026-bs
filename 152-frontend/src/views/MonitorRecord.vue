<template><DataPage title="监护记录" description="记录编号、作业票号、监护项目、记录人、记录时间和现场状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'SAFETY', 'GUARDIAN']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addMonitorRecord, deleteMonitorRecord, finishMonitorRecord, getMonitorRecordPage, processMonitorRecord, updateMonitorRecord } from '../api'
const api = { page: getMonitorRecordPage, add: addMonitorRecord, update: updateMonitorRecord, delete: deleteMonitorRecord, process: processMonitorRecord, finish: finishMonitorRecord }
const columns = [{ prop: 'recordNo', label: '记录编号' }, { prop: 'recordName', label: '作业票号' }, { prop: 'category', label: '监护项目' }, { prop: 'ownerName', label: '记录人' }, { prop: 'planTime', label: '记录时间' }, { prop: 'status', label: '现场状态' }, { prop: 'remark', label: '监护说明' }]
const formFields = [{ prop: 'recordNo', label: '记录编号' }, { prop: 'recordName', label: '作业票号' }, { prop: 'category', label: '监护项目' }, { prop: 'ownerName', label: '记录人' }, { prop: 'planTime', label: '记录时间' }, { prop: 'status', label: '现场状态', type: 'select' }, { prop: 'remark', label: '监护说明', type: 'textarea' }]
</script>

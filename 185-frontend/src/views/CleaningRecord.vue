<template><DataPage title="保洁记录" description="记录编号、保洁任务、记录类型、保洁人员、完成时间和记录状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'SANITATION', 'CLEANER', 'INSPECTOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addCleaningRecord, deleteCleaningRecord, finishCleaningRecord, getCleaningRecordPage, processCleaningRecord, updateCleaningRecord } from '../api'
const api = { page: getCleaningRecordPage, add: addCleaningRecord, update: updateCleaningRecord, delete: deleteCleaningRecord, process: processCleaningRecord, finish: finishCleaningRecord }
const columns = [{ prop: 'recordNo', label: '记录编号' }, { prop: 'recordName', label: '保洁任务' }, { prop: 'category', label: '记录类型' }, { prop: 'ownerName', label: '保洁人员' }, { prop: 'planTime', label: '完成时间' }, { prop: 'status', label: '记录状态' }, { prop: 'remark', label: '记录说明' }]
const formFields = [{ prop: 'recordNo', label: '记录编号' }, { prop: 'recordName', label: '保洁任务' }, { prop: 'category', label: '记录类型' }, { prop: 'ownerName', label: '保洁人员' }, { prop: 'planTime', label: '完成时间' }, { prop: 'status', label: '记录状态', type: 'select' }, { prop: 'remark', label: '记录说明', type: 'textarea' }]
</script>

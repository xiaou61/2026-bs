<template><DataPage title="整改任务" description="整改编号、整改对象、问题类型、责任人员、整改时间和整改状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'COMMUNITY', 'SUPERVISOR', 'VOLUNTEER', 'RESIDENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addCorrectionTask, deleteCorrectionTask, finishCorrectionTask, getCorrectionTaskPage, processCorrectionTask, updateCorrectionTask } from '../api'
const api = { page: getCorrectionTaskPage, add: addCorrectionTask, update: updateCorrectionTask, delete: deleteCorrectionTask, process: processCorrectionTask, finish: finishCorrectionTask }
const columns = [{ prop: 'recordNo', label: '整改编号' }, { prop: 'recordName', label: '整改对象' }, { prop: 'category', label: '问题类型' }, { prop: 'ownerName', label: '责任人员' }, { prop: 'planTime', label: '整改时间' }, { prop: 'status', label: '整改状态' }, { prop: 'remark', label: '整改说明' }]
const formFields = [{ prop: 'recordNo', label: '整改编号' }, { prop: 'recordName', label: '整改对象' }, { prop: 'category', label: '问题类型' }, { prop: 'ownerName', label: '责任人员' }, { prop: 'planTime', label: '整改时间' }, { prop: 'status', label: '整改状态', type: 'select' }, { prop: 'remark', label: '整改说明', type: 'textarea' }]
</script>

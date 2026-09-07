<template><DataPage title="调度任务" description="任务编号、调度物资、调度类型、调度人员、调度时间和任务状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'DISPATCH', 'WAREHOUSE', 'APPLICANT', 'AUDITOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addDispatchTask, deleteDispatchTask, finishDispatchTask, getDispatchTaskPage, processDispatchTask, updateDispatchTask } from '../api'
const api = { page: getDispatchTaskPage, add: addDispatchTask, update: updateDispatchTask, delete: deleteDispatchTask, process: processDispatchTask, finish: finishDispatchTask }
const columns = [{ prop: 'recordNo', label: '任务编号' }, { prop: 'recordName', label: '调度物资' }, { prop: 'category', label: '调度类型' }, { prop: 'ownerName', label: '调度人员' }, { prop: 'planTime', label: '调度时间' }, { prop: 'status', label: '任务状态' }, { prop: 'remark', label: '任务说明' }]
const formFields = [{ prop: 'recordNo', label: '任务编号' }, { prop: 'recordName', label: '调度物资' }, { prop: 'category', label: '调度类型' }, { prop: 'ownerName', label: '调度人员' }, { prop: 'planTime', label: '调度时间' }, { prop: 'status', label: '任务状态', type: 'select' }, { prop: 'remark', label: '任务说明', type: 'textarea' }]
</script>

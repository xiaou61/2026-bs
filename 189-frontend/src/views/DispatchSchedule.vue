<template><DataPage title="机手调度" description="调度编号、预约任务、作业类型、调度人员、调度时间和调度状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'STATION', 'DISPATCH', 'DRIVER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addDispatchSchedule, deleteDispatchSchedule, finishDispatchSchedule, getDispatchSchedulePage, processDispatchSchedule, updateDispatchSchedule } from '../api'
const api = { page: getDispatchSchedulePage, add: addDispatchSchedule, update: updateDispatchSchedule, delete: deleteDispatchSchedule, process: processDispatchSchedule, finish: finishDispatchSchedule }
const columns = [{ prop: 'recordNo', label: '调度编号' }, { prop: 'recordName', label: '预约任务' }, { prop: 'category', label: '作业类型' }, { prop: 'ownerName', label: '调度人员' }, { prop: 'planTime', label: '调度时间' }, { prop: 'status', label: '调度状态' }, { prop: 'remark', label: '调度说明' }]
const formFields = [{ prop: 'recordNo', label: '调度编号' }, { prop: 'recordName', label: '预约任务' }, { prop: 'category', label: '作业类型' }, { prop: 'ownerName', label: '调度人员' }, { prop: 'planTime', label: '调度时间' }, { prop: 'status', label: '调度状态', type: 'select' }, { prop: 'remark', label: '调度说明', type: 'textarea' }]
</script>

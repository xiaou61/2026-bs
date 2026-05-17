<template><DataPage title="人员排班" description="排班编号、服务预约、排班类型、调度人员、排班时间和排班状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'AGENCY', 'DISPATCH', 'WORKER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addScheduleDispatch, deleteScheduleDispatch, finishScheduleDispatch, getScheduleDispatchPage, processScheduleDispatch, updateScheduleDispatch } from '../api'
const api = { page: getScheduleDispatchPage, add: addScheduleDispatch, update: updateScheduleDispatch, delete: deleteScheduleDispatch, process: processScheduleDispatch, finish: finishScheduleDispatch }
const columns = [{ prop: 'recordNo', label: '排班编号' }, { prop: 'recordName', label: '服务预约' }, { prop: 'category', label: '排班类型' }, { prop: 'ownerName', label: '调度人员' }, { prop: 'planTime', label: '排班时间' }, { prop: 'status', label: '排班状态' }, { prop: 'remark', label: '排班说明' }]
const formFields = [{ prop: 'recordNo', label: '排班编号' }, { prop: 'recordName', label: '服务预约' }, { prop: 'category', label: '排班类型' }, { prop: 'ownerName', label: '调度人员' }, { prop: 'planTime', label: '排班时间' }, { prop: 'status', label: '排班状态', type: 'select' }, { prop: 'remark', label: '排班说明', type: 'textarea' }]
</script>

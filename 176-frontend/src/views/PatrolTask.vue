<template><DataPage title="巡线任务" description="任务编号、巡线区段、任务类型、巡线人员、计划时间和任务状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'DISPATCH', 'PATROL', 'SUPERVISOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addPatrolTask, deletePatrolTask, finishPatrolTask, getPatrolTaskPage, processPatrolTask, updatePatrolTask } from '../api'
const api = { page: getPatrolTaskPage, add: addPatrolTask, update: updatePatrolTask, delete: deletePatrolTask, process: processPatrolTask, finish: finishPatrolTask }
const columns = [{ prop: 'recordNo', label: '任务编号' }, { prop: 'recordName', label: '巡线区段' }, { prop: 'category', label: '任务类型' }, { prop: 'ownerName', label: '巡线人员' }, { prop: 'planTime', label: '计划时间' }, { prop: 'status', label: '任务状态' }, { prop: 'remark', label: '任务说明' }]
const formFields = [{ prop: 'recordNo', label: '任务编号' }, { prop: 'recordName', label: '巡线区段' }, { prop: 'category', label: '任务类型' }, { prop: 'ownerName', label: '巡线人员' }, { prop: 'planTime', label: '计划时间' }, { prop: 'status', label: '任务状态', type: 'select' }, { prop: 'remark', label: '任务说明', type: 'textarea' }]
</script>

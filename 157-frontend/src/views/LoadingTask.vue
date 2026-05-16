<template><DataPage title="装卸任务" description="任务编号、货物批次、装卸类型、负责人、作业时间和任务状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'DISPATCHER', 'YARDMASTER', 'CARRIER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addLoadingTask, deleteLoadingTask, finishLoadingTask, getLoadingTaskPage, processLoadingTask, updateLoadingTask } from '../api'
const api = { page: getLoadingTaskPage, add: addLoadingTask, update: updateLoadingTask, delete: deleteLoadingTask, process: processLoadingTask, finish: finishLoadingTask }
const columns = [{ prop: 'recordNo', label: '任务编号' }, { prop: 'recordName', label: '货物批次' }, { prop: 'category', label: '装卸类型' }, { prop: 'ownerName', label: '负责人' }, { prop: 'planTime', label: '作业时间' }, { prop: 'status', label: '任务状态' }, { prop: 'remark', label: '任务说明' }]
const formFields = [{ prop: 'recordNo', label: '任务编号' }, { prop: 'recordName', label: '货物批次' }, { prop: 'category', label: '装卸类型' }, { prop: 'ownerName', label: '负责人' }, { prop: 'planTime', label: '作业时间' }, { prop: 'status', label: '任务状态', type: 'select' }, { prop: 'remark', label: '任务说明', type: 'textarea' }]
</script>

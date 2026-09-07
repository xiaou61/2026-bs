<template><DataPage title="节能任务" description="任务编号、任务名称、节能类型、负责人、执行时间和任务状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'LOGISTICS', 'COUNSELOR', 'ENERGY', 'STUDENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addSavingTask, deleteSavingTask, finishSavingTask, getSavingTaskPage, processSavingTask, updateSavingTask } from '../api'
const api = { page: getSavingTaskPage, add: addSavingTask, update: updateSavingTask, delete: deleteSavingTask, process: processSavingTask, finish: finishSavingTask }
const columns = [{ prop: 'recordNo', label: '任务编号' }, { prop: 'recordName', label: '任务名称' }, { prop: 'category', label: '节能类型' }, { prop: 'ownerName', label: '负责人' }, { prop: 'planTime', label: '执行时间' }, { prop: 'status', label: '任务状态' }, { prop: 'remark', label: '任务说明' }]
const formFields = [{ prop: 'recordNo', label: '任务编号' }, { prop: 'recordName', label: '任务名称' }, { prop: 'category', label: '节能类型' }, { prop: 'ownerName', label: '负责人' }, { prop: 'planTime', label: '执行时间' }, { prop: 'status', label: '任务状态', type: 'select' }, { prop: 'remark', label: '任务说明', type: 'textarea' }]
</script>

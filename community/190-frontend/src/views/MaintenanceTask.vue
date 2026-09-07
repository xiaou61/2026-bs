<template><DataPage title="保养任务" description="任务编号、保养计划、任务类型、执行人员、执行时间和任务状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'PROPERTY', 'TECHNICIAN', 'INSPECTOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addMaintenanceTask, deleteMaintenanceTask, finishMaintenanceTask, getMaintenanceTaskPage, processMaintenanceTask, updateMaintenanceTask } from '../api'
const api = { page: getMaintenanceTaskPage, add: addMaintenanceTask, update: updateMaintenanceTask, delete: deleteMaintenanceTask, process: processMaintenanceTask, finish: finishMaintenanceTask }
const columns = [{ prop: 'recordNo', label: '任务编号' }, { prop: 'recordName', label: '保养计划' }, { prop: 'category', label: '任务类型' }, { prop: 'ownerName', label: '执行人员' }, { prop: 'planTime', label: '执行时间' }, { prop: 'status', label: '任务状态' }, { prop: 'remark', label: '任务说明' }]
const formFields = [{ prop: 'recordNo', label: '任务编号' }, { prop: 'recordName', label: '保养计划' }, { prop: 'category', label: '任务类型' }, { prop: 'ownerName', label: '执行人员' }, { prop: 'planTime', label: '执行时间' }, { prop: 'status', label: '任务状态', type: 'select' }, { prop: 'remark', label: '任务说明', type: 'textarea' }]
</script>

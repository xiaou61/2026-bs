<template><DataPage title="寻回任务" description="任务编号、任务物品、任务类型、执行人、执行时间和任务状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'SERVICE', 'SECURITY', 'SCENIC']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addSearchTask, deleteSearchTask, finishSearchTask, getSearchTaskPage, processSearchTask, updateSearchTask } from '../api'
const api = { page: getSearchTaskPage, add: addSearchTask, update: updateSearchTask, delete: deleteSearchTask, process: processSearchTask, finish: finishSearchTask }
const columns = [{ prop: 'recordNo', label: '任务编号' }, { prop: 'recordName', label: '任务物品' }, { prop: 'category', label: '任务类型' }, { prop: 'ownerName', label: '执行人' }, { prop: 'planTime', label: '执行时间' }, { prop: 'status', label: '任务状态' }, { prop: 'remark', label: '任务说明' }]
const formFields = [{ prop: 'recordNo', label: '任务编号' }, { prop: 'recordName', label: '任务物品' }, { prop: 'category', label: '任务类型' }, { prop: 'ownerName', label: '执行人' }, { prop: 'planTime', label: '执行时间' }, { prop: 'status', label: '任务状态', type: 'select' }, { prop: 'remark', label: '任务说明', type: 'textarea' }]
</script>

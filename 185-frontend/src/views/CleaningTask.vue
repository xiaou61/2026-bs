<template><DataPage title="保洁任务" description="任务编号、公厕点位、任务类型、保洁人员、派发时间和任务状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'SANITATION', 'CLEANER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addCleaningTask, deleteCleaningTask, finishCleaningTask, getCleaningTaskPage, processCleaningTask, updateCleaningTask } from '../api'
const api = { page: getCleaningTaskPage, add: addCleaningTask, update: updateCleaningTask, delete: deleteCleaningTask, process: processCleaningTask, finish: finishCleaningTask }
const columns = [{ prop: 'recordNo', label: '任务编号' }, { prop: 'recordName', label: '公厕点位' }, { prop: 'category', label: '任务类型' }, { prop: 'ownerName', label: '保洁人员' }, { prop: 'planTime', label: '派发时间' }, { prop: 'status', label: '任务状态' }, { prop: 'remark', label: '任务说明' }]
const formFields = [{ prop: 'recordNo', label: '任务编号' }, { prop: 'recordName', label: '公厕点位' }, { prop: 'category', label: '任务类型' }, { prop: 'ownerName', label: '保洁人员' }, { prop: 'planTime', label: '派发时间' }, { prop: 'status', label: '任务状态', type: 'select' }, { prop: 'remark', label: '任务说明', type: 'textarea' }]
</script>

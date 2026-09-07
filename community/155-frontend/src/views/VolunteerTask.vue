<template><DataPage title="志愿任务" description="任务编号、任务名称、服务类型、负责人、服务时间和任务状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'ORGANIZER', 'VOLUNTEER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addVolunteerTask, deleteVolunteerTask, finishVolunteerTask, getVolunteerTaskPage, processVolunteerTask, updateVolunteerTask } from '../api'
const api = { page: getVolunteerTaskPage, add: addVolunteerTask, update: updateVolunteerTask, delete: deleteVolunteerTask, process: processVolunteerTask, finish: finishVolunteerTask }
const columns = [{ prop: 'recordNo', label: '任务编号' }, { prop: 'recordName', label: '任务名称' }, { prop: 'category', label: '服务类型' }, { prop: 'ownerName', label: '负责人' }, { prop: 'planTime', label: '服务时间' }, { prop: 'status', label: '任务状态' }, { prop: 'remark', label: '任务说明' }]
const formFields = [{ prop: 'recordNo', label: '任务编号' }, { prop: 'recordName', label: '任务名称' }, { prop: 'category', label: '服务类型' }, { prop: 'ownerName', label: '负责人' }, { prop: 'planTime', label: '服务时间' }, { prop: 'status', label: '任务状态', type: 'select' }, { prop: 'remark', label: '任务说明', type: 'textarea' }]
</script>

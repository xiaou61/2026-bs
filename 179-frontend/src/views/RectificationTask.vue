<template><DataPage title="整改任务" description="任务编号、问题事项、整改类型、责任人员、整改时间和任务状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'ACADEMIC', 'INSPECTOR', 'COUNSELOR', 'TEACHER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addRectificationTask, deleteRectificationTask, finishRectificationTask, getRectificationTaskPage, processRectificationTask, updateRectificationTask } from '../api'
const api = { page: getRectificationTaskPage, add: addRectificationTask, update: updateRectificationTask, delete: deleteRectificationTask, process: processRectificationTask, finish: finishRectificationTask }
const columns = [{ prop: 'recordNo', label: '任务编号' }, { prop: 'recordName', label: '问题事项' }, { prop: 'category', label: '整改类型' }, { prop: 'ownerName', label: '责任人员' }, { prop: 'planTime', label: '整改时间' }, { prop: 'status', label: '任务状态' }, { prop: 'remark', label: '任务说明' }]
const formFields = [{ prop: 'recordNo', label: '任务编号' }, { prop: 'recordName', label: '问题事项' }, { prop: 'category', label: '整改类型' }, { prop: 'ownerName', label: '责任人员' }, { prop: 'planTime', label: '整改时间' }, { prop: 'status', label: '任务状态', type: 'select' }, { prop: 'remark', label: '任务说明', type: 'textarea' }]
</script>

<template><DataPage title="整改反馈" description="反馈编号、整改任务、反馈类型、反馈人员、反馈时间和反馈状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'ACADEMIC', 'INSPECTOR', 'COUNSELOR', 'TEACHER', 'STUDENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addRectificationFeedback, deleteRectificationFeedback, finishRectificationFeedback, getRectificationFeedbackPage, processRectificationFeedback, updateRectificationFeedback } from '../api'
const api = { page: getRectificationFeedbackPage, add: addRectificationFeedback, update: updateRectificationFeedback, delete: deleteRectificationFeedback, process: processRectificationFeedback, finish: finishRectificationFeedback }
const columns = [{ prop: 'recordNo', label: '反馈编号' }, { prop: 'recordName', label: '整改任务' }, { prop: 'category', label: '反馈类型' }, { prop: 'ownerName', label: '反馈人员' }, { prop: 'planTime', label: '反馈时间' }, { prop: 'status', label: '反馈状态' }, { prop: 'remark', label: '反馈说明' }]
const formFields = [{ prop: 'recordNo', label: '反馈编号' }, { prop: 'recordName', label: '整改任务' }, { prop: 'category', label: '反馈类型' }, { prop: 'ownerName', label: '反馈人员' }, { prop: 'planTime', label: '反馈时间' }, { prop: 'status', label: '反馈状态', type: 'select' }, { prop: 'remark', label: '反馈说明', type: 'textarea' }]
</script>

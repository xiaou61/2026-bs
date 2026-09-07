<template><DataPage title="游客评价" description="评价编号、评价对象、评价类型、游客姓名、评价时间和处理状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'MANAGER', 'GUIDE', 'VISITOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addVisitorFeedback, deleteVisitorFeedback, finishVisitorFeedback, getVisitorFeedbackPage, processVisitorFeedback, updateVisitorFeedback } from '../api'
const api = { page: getVisitorFeedbackPage, add: addVisitorFeedback, update: updateVisitorFeedback, delete: deleteVisitorFeedback, process: processVisitorFeedback, finish: finishVisitorFeedback }
const columns = [{ prop: 'recordNo', label: '评价编号' }, { prop: 'recordName', label: '评价对象' }, { prop: 'category', label: '评价类型' }, { prop: 'ownerName', label: '游客姓名' }, { prop: 'planTime', label: '评价时间' }, { prop: 'status', label: '处理状态' }, { prop: 'remark', label: '评价内容' }]
const formFields = [{ prop: 'recordNo', label: '评价编号' }, { prop: 'recordName', label: '评价对象' }, { prop: 'category', label: '评价类型' }, { prop: 'ownerName', label: '游客姓名' }, { prop: 'planTime', label: '评价时间' }, { prop: 'status', label: '处理状态', type: 'select' }, { prop: 'remark', label: '评价内容', type: 'textarea' }]
</script>

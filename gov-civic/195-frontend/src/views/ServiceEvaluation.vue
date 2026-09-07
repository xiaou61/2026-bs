<template><DataPage title="满意评价" description="评价编号、办理事项、评价类型、评价人员、评价时间和评价状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CENTER', 'SUPERVISE', 'CITIZEN']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addServiceEvaluation, deleteServiceEvaluation, finishServiceEvaluation, getServiceEvaluationPage, processServiceEvaluation, updateServiceEvaluation } from '../api'
const api = { page: getServiceEvaluationPage, add: addServiceEvaluation, update: updateServiceEvaluation, delete: deleteServiceEvaluation, process: processServiceEvaluation, finish: finishServiceEvaluation }
const columns = [{ prop: 'recordNo', label: '评价编号' }, { prop: 'recordName', label: '办理事项' }, { prop: 'category', label: '评价类型' }, { prop: 'ownerName', label: '评价人员' }, { prop: 'planTime', label: '评价时间' }, { prop: 'status', label: '评价状态' }, { prop: 'remark', label: '评价说明' }]
const formFields = [{ prop: 'recordNo', label: '评价编号' }, { prop: 'recordName', label: '办理事项' }, { prop: 'category', label: '评价类型' }, { prop: 'ownerName', label: '评价人员' }, { prop: 'planTime', label: '评价时间' }, { prop: 'status', label: '评价状态', type: 'select' }, { prop: 'remark', label: '评价说明', type: 'textarea' }]
</script>

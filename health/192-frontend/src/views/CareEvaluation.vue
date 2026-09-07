<template><DataPage title="护工评价" description="评价编号、服务记录、评价类型、评价人员、评价时间和评价状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'COORDINATOR', 'FINANCE', 'FAMILY']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addCareEvaluation, deleteCareEvaluation, finishCareEvaluation, getCareEvaluationPage, processCareEvaluation, updateCareEvaluation } from '../api'
const api = { page: getCareEvaluationPage, add: addCareEvaluation, update: updateCareEvaluation, delete: deleteCareEvaluation, process: processCareEvaluation, finish: finishCareEvaluation }
const columns = [{ prop: 'recordNo', label: '评价编号' }, { prop: 'recordName', label: '服务记录' }, { prop: 'category', label: '评价类型' }, { prop: 'ownerName', label: '评价人员' }, { prop: 'planTime', label: '评价时间' }, { prop: 'status', label: '评价状态' }, { prop: 'remark', label: '评价说明' }]
const formFields = [{ prop: 'recordNo', label: '评价编号' }, { prop: 'recordName', label: '服务记录' }, { prop: 'category', label: '评价类型' }, { prop: 'ownerName', label: '评价人员' }, { prop: 'planTime', label: '评价时间' }, { prop: 'status', label: '评价状态', type: 'select' }, { prop: 'remark', label: '评价说明', type: 'textarea' }]
</script>

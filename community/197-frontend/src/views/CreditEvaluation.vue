<template><DataPage title="信用评价" description="评价编号、上门记录、评价类型、评价人员、评价时间和评价状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'AGENCY', 'QUALITY', 'RESIDENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addCreditEvaluation, deleteCreditEvaluation, finishCreditEvaluation, getCreditEvaluationPage, processCreditEvaluation, updateCreditEvaluation } from '../api'
const api = { page: getCreditEvaluationPage, add: addCreditEvaluation, update: updateCreditEvaluation, delete: deleteCreditEvaluation, process: processCreditEvaluation, finish: finishCreditEvaluation }
const columns = [{ prop: 'recordNo', label: '评价编号' }, { prop: 'recordName', label: '上门记录' }, { prop: 'category', label: '评价类型' }, { prop: 'ownerName', label: '评价人员' }, { prop: 'planTime', label: '评价时间' }, { prop: 'status', label: '评价状态' }, { prop: 'remark', label: '评价说明' }]
const formFields = [{ prop: 'recordNo', label: '评价编号' }, { prop: 'recordName', label: '上门记录' }, { prop: 'category', label: '评价类型' }, { prop: 'ownerName', label: '评价人员' }, { prop: 'planTime', label: '评价时间' }, { prop: 'status', label: '评价状态', type: 'select' }, { prop: 'remark', label: '评价说明', type: 'textarea' }]
</script>

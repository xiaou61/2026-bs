<template><DataPage title="信用评价" description="评价编号、评价对象、评价类型、评价人、评价时间和处理状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'OPERATOR', 'SELLER', 'BUYER', 'ARBITER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addCreditEvaluation, deleteCreditEvaluation, finishCreditEvaluation, getCreditEvaluationPage, processCreditEvaluation, updateCreditEvaluation } from '../api'
const api = { page: getCreditEvaluationPage, add: addCreditEvaluation, update: updateCreditEvaluation, delete: deleteCreditEvaluation, process: processCreditEvaluation, finish: finishCreditEvaluation }
const columns = [{ prop: 'recordNo', label: '评价编号' }, { prop: 'recordName', label: '评价对象' }, { prop: 'category', label: '评价类型' }, { prop: 'ownerName', label: '评价人' }, { prop: 'planTime', label: '评价时间' }, { prop: 'status', label: '处理状态' }, { prop: 'remark', label: '评价内容' }]
const formFields = [{ prop: 'recordNo', label: '评价编号' }, { prop: 'recordName', label: '评价对象' }, { prop: 'category', label: '评价类型' }, { prop: 'ownerName', label: '评价人' }, { prop: 'planTime', label: '评价时间' }, { prop: 'status', label: '处理状态', type: 'select' }, { prop: 'remark', label: '评价内容', type: 'textarea' }]
</script>

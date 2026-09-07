<template><DataPage title="样品借还" description="借还编号、借还样品、借还类型、借用主播、借用时间和借还状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'BASE', 'ANCHOR', 'SELECTOR', 'SAMPLE', 'MERCHANT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addSampleLoan, deleteSampleLoan, finishSampleLoan, getSampleLoanPage, processSampleLoan, updateSampleLoan } from '../api'
const api = { page: getSampleLoanPage, add: addSampleLoan, update: updateSampleLoan, delete: deleteSampleLoan, process: processSampleLoan, finish: finishSampleLoan }
const columns = [{ prop: 'recordNo', label: '借还编号' }, { prop: 'recordName', label: '借还样品' }, { prop: 'category', label: '借还类型' }, { prop: 'ownerName', label: '借用主播' }, { prop: 'planTime', label: '借用时间' }, { prop: 'status', label: '借还状态' }, { prop: 'remark', label: '借还说明' }]
const formFields = [{ prop: 'recordNo', label: '借还编号' }, { prop: 'recordName', label: '借还样品' }, { prop: 'category', label: '借还类型' }, { prop: 'ownerName', label: '借用主播' }, { prop: 'planTime', label: '借用时间' }, { prop: 'status', label: '借还状态', type: 'select' }, { prop: 'remark', label: '借还说明', type: 'textarea' }]
</script>

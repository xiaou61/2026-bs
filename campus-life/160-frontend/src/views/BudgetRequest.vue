<template><DataPage title="预算申请" description="预算编号、活动名称、预算类型、申请人、申请时间和预算状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'UNION', 'CLUB', 'TREASURER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addBudgetRequest, deleteBudgetRequest, finishBudgetRequest, getBudgetRequestPage, processBudgetRequest, updateBudgetRequest } from '../api'
const api = { page: getBudgetRequestPage, add: addBudgetRequest, update: updateBudgetRequest, delete: deleteBudgetRequest, process: processBudgetRequest, finish: finishBudgetRequest }
const columns = [{ prop: 'recordNo', label: '预算编号' }, { prop: 'recordName', label: '活动名称' }, { prop: 'category', label: '预算类型' }, { prop: 'ownerName', label: '申请人' }, { prop: 'planTime', label: '申请时间' }, { prop: 'status', label: '预算状态' }, { prop: 'remark', label: '预算说明' }]
const formFields = [{ prop: 'recordNo', label: '预算编号' }, { prop: 'recordName', label: '活动名称' }, { prop: 'category', label: '预算类型' }, { prop: 'ownerName', label: '申请人' }, { prop: 'planTime', label: '申请时间' }, { prop: 'status', label: '预算状态', type: 'select' }, { prop: 'remark', label: '预算说明', type: 'textarea' }]
</script>

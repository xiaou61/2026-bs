<template><DataPage title="预算明细" description="明细编号、费用项目、费用类型、填报人、填报时间和明细状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'UNION', 'CLUB', 'TREASURER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addBudgetLineItem, deleteBudgetLineItem, finishBudgetLineItem, getBudgetLineItemPage, processBudgetLineItem, updateBudgetLineItem } from '../api'
const api = { page: getBudgetLineItemPage, add: addBudgetLineItem, update: updateBudgetLineItem, delete: deleteBudgetLineItem, process: processBudgetLineItem, finish: finishBudgetLineItem }
const columns = [{ prop: 'recordNo', label: '明细编号' }, { prop: 'recordName', label: '费用项目' }, { prop: 'category', label: '费用类型' }, { prop: 'ownerName', label: '填报人' }, { prop: 'planTime', label: '填报时间' }, { prop: 'status', label: '明细状态' }, { prop: 'remark', label: '明细说明' }]
const formFields = [{ prop: 'recordNo', label: '明细编号' }, { prop: 'recordName', label: '费用项目' }, { prop: 'category', label: '费用类型' }, { prop: 'ownerName', label: '填报人' }, { prop: 'planTime', label: '填报时间' }, { prop: 'status', label: '明细状态', type: 'select' }, { prop: 'remark', label: '明细说明', type: 'textarea' }]
</script>

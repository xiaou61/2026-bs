<template><DataPage title="费用预算" description="预算编号、预算项目、预算类型、编制人员、编制时间和预算状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'AGENCY', 'PLANNER', 'FINANCE', 'LEADER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addCostBudget, deleteCostBudget, finishCostBudget, getCostBudgetPage, processCostBudget, updateCostBudget } from '../api'
const api = { page: getCostBudgetPage, add: addCostBudget, update: updateCostBudget, delete: deleteCostBudget, process: processCostBudget, finish: finishCostBudget }
const columns = [{ prop: 'recordNo', label: '预算编号' }, { prop: 'recordName', label: '预算项目' }, { prop: 'category', label: '预算类型' }, { prop: 'ownerName', label: '编制人员' }, { prop: 'planTime', label: '编制时间' }, { prop: 'status', label: '预算状态' }, { prop: 'remark', label: '预算说明' }]
const formFields = [{ prop: 'recordNo', label: '预算编号' }, { prop: 'recordName', label: '预算项目' }, { prop: 'category', label: '预算类型' }, { prop: 'ownerName', label: '编制人员' }, { prop: 'planTime', label: '编制时间' }, { prop: 'status', label: '预算状态', type: 'select' }, { prop: 'remark', label: '预算说明', type: 'textarea' }]
</script>

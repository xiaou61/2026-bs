<template><DataPage title="折扣执行" description="执行编号、执行商品、折扣类型、执行人、执行时间和执行状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'MANAGER', 'CLERK', 'MARKETING']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addDiscountExecution, deleteDiscountExecution, finishDiscountExecution, getDiscountExecutionPage, processDiscountExecution, updateDiscountExecution } from '../api'
const api = { page: getDiscountExecutionPage, add: addDiscountExecution, update: updateDiscountExecution, delete: deleteDiscountExecution, process: processDiscountExecution, finish: finishDiscountExecution }
const columns = [{ prop: 'recordNo', label: '执行编号' }, { prop: 'recordName', label: '执行商品' }, { prop: 'category', label: '折扣类型' }, { prop: 'ownerName', label: '执行人' }, { prop: 'planTime', label: '执行时间' }, { prop: 'status', label: '执行状态' }, { prop: 'remark', label: '执行说明' }]
const formFields = [{ prop: 'recordNo', label: '执行编号' }, { prop: 'recordName', label: '执行商品' }, { prop: 'category', label: '折扣类型' }, { prop: 'ownerName', label: '执行人' }, { prop: 'planTime', label: '执行时间' }, { prop: 'status', label: '执行状态', type: 'select' }, { prop: 'remark', label: '执行说明', type: 'textarea' }]
</script>

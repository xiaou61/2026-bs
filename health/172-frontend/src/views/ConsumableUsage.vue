<template><DataPage title="耗材使用" description="使用编号、使用耗材、使用类型、登记人员、使用时间和使用状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'DENTIST', 'NURSE', 'CASHIER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addConsumableUsage, deleteConsumableUsage, finishConsumableUsage, getConsumableUsagePage, processConsumableUsage, updateConsumableUsage } from '../api'
const api = { page: getConsumableUsagePage, add: addConsumableUsage, update: updateConsumableUsage, delete: deleteConsumableUsage, process: processConsumableUsage, finish: finishConsumableUsage }
const columns = [{ prop: 'recordNo', label: '使用编号' }, { prop: 'recordName', label: '使用耗材' }, { prop: 'category', label: '使用类型' }, { prop: 'ownerName', label: '登记人员' }, { prop: 'planTime', label: '使用时间' }, { prop: 'status', label: '使用状态' }, { prop: 'remark', label: '使用说明' }]
const formFields = [{ prop: 'recordNo', label: '使用编号' }, { prop: 'recordName', label: '使用耗材' }, { prop: 'category', label: '使用类型' }, { prop: 'ownerName', label: '登记人员' }, { prop: 'planTime', label: '使用时间' }, { prop: 'status', label: '使用状态', type: 'select' }, { prop: 'remark', label: '使用说明', type: 'textarea' }]
</script>

<template><DataPage title="报修工单" description="工单编号、报修位置、报修类型、报修业主、提交时间和工单状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'PROPERTY', 'OWNER', 'DISPATCH', 'WORKER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addRepairOrder, deleteRepairOrder, finishRepairOrder, getRepairOrderPage, processRepairOrder, updateRepairOrder } from '../api'
const api = { page: getRepairOrderPage, add: addRepairOrder, update: updateRepairOrder, delete: deleteRepairOrder, process: processRepairOrder, finish: finishRepairOrder }
const columns = [{ prop: 'recordNo', label: '工单编号' }, { prop: 'recordName', label: '报修位置' }, { prop: 'category', label: '报修类型' }, { prop: 'ownerName', label: '报修业主' }, { prop: 'planTime', label: '提交时间' }, { prop: 'status', label: '工单状态' }, { prop: 'remark', label: '工单说明' }]
const formFields = [{ prop: 'recordNo', label: '工单编号' }, { prop: 'recordName', label: '报修位置' }, { prop: 'category', label: '报修类型' }, { prop: 'ownerName', label: '报修业主' }, { prop: 'planTime', label: '提交时间' }, { prop: 'status', label: '工单状态', type: 'select' }, { prop: 'remark', label: '工单说明', type: 'textarea' }]
</script>

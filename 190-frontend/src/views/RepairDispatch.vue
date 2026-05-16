<template><DataPage title="维修派工" description="派工编号、关联工单、维修类型、维修技师、派工时间和派工状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'PROPERTY', 'DISPATCH', 'TECHNICIAN']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addRepairDispatch, deleteRepairDispatch, finishRepairDispatch, getRepairDispatchPage, processRepairDispatch, updateRepairDispatch } from '../api'
const api = { page: getRepairDispatchPage, add: addRepairDispatch, update: updateRepairDispatch, delete: deleteRepairDispatch, process: processRepairDispatch, finish: finishRepairDispatch }
const columns = [{ prop: 'recordNo', label: '派工编号' }, { prop: 'recordName', label: '关联工单' }, { prop: 'category', label: '维修类型' }, { prop: 'ownerName', label: '维修技师' }, { prop: 'planTime', label: '派工时间' }, { prop: 'status', label: '派工状态' }, { prop: 'remark', label: '派工说明' }]
const formFields = [{ prop: 'recordNo', label: '派工编号' }, { prop: 'recordName', label: '关联工单' }, { prop: 'category', label: '维修类型' }, { prop: 'ownerName', label: '维修技师' }, { prop: 'planTime', label: '派工时间' }, { prop: 'status', label: '派工状态', type: 'select' }, { prop: 'remark', label: '派工说明', type: 'textarea' }]
</script>

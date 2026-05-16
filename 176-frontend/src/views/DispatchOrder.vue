<template><DataPage title="故障派工" description="派工编号、故障事项、派工类型、检修人员、派工时间和派工状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'DISPATCH', 'REPAIR', 'SUPERVISOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addDispatchOrder, deleteDispatchOrder, finishDispatchOrder, getDispatchOrderPage, processDispatchOrder, updateDispatchOrder } from '../api'
const api = { page: getDispatchOrderPage, add: addDispatchOrder, update: updateDispatchOrder, delete: deleteDispatchOrder, process: processDispatchOrder, finish: finishDispatchOrder }
const columns = [{ prop: 'recordNo', label: '派工编号' }, { prop: 'recordName', label: '故障事项' }, { prop: 'category', label: '派工类型' }, { prop: 'ownerName', label: '检修人员' }, { prop: 'planTime', label: '派工时间' }, { prop: 'status', label: '派工状态' }, { prop: 'remark', label: '派工说明' }]
const formFields = [{ prop: 'recordNo', label: '派工编号' }, { prop: 'recordName', label: '故障事项' }, { prop: 'category', label: '派工类型' }, { prop: 'ownerName', label: '检修人员' }, { prop: 'planTime', label: '派工时间' }, { prop: 'status', label: '派工状态', type: 'select' }, { prop: 'remark', label: '派工说明', type: 'textarea' }]
</script>

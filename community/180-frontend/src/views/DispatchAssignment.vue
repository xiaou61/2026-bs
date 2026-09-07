<template><DataPage title="派单分配" description="派单编号、报修工单、派单类型、维修人员、派单时间和派单状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'PROPERTY', 'DISPATCH', 'WORKER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addDispatchAssignment, deleteDispatchAssignment, finishDispatchAssignment, getDispatchAssignmentPage, processDispatchAssignment, updateDispatchAssignment } from '../api'
const api = { page: getDispatchAssignmentPage, add: addDispatchAssignment, update: updateDispatchAssignment, delete: deleteDispatchAssignment, process: processDispatchAssignment, finish: finishDispatchAssignment }
const columns = [{ prop: 'recordNo', label: '派单编号' }, { prop: 'recordName', label: '报修工单' }, { prop: 'category', label: '派单类型' }, { prop: 'ownerName', label: '维修人员' }, { prop: 'planTime', label: '派单时间' }, { prop: 'status', label: '派单状态' }, { prop: 'remark', label: '派单说明' }]
const formFields = [{ prop: 'recordNo', label: '派单编号' }, { prop: 'recordName', label: '报修工单' }, { prop: 'category', label: '派单类型' }, { prop: 'ownerName', label: '维修人员' }, { prop: 'planTime', label: '派单时间' }, { prop: 'status', label: '派单状态', type: 'select' }, { prop: 'remark', label: '派单说明', type: 'textarea' }]
</script>

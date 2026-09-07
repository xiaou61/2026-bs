<template><DataPage title="服务派单" description="派单编号、预约订单、服务类型、护工人员、派单时间和派单状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'COORDINATOR', 'CAREGIVER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addServiceAssignment, deleteServiceAssignment, finishServiceAssignment, getServiceAssignmentPage, processServiceAssignment, updateServiceAssignment } from '../api'
const api = { page: getServiceAssignmentPage, add: addServiceAssignment, update: updateServiceAssignment, delete: deleteServiceAssignment, process: processServiceAssignment, finish: finishServiceAssignment }
const columns = [{ prop: 'recordNo', label: '派单编号' }, { prop: 'recordName', label: '预约订单' }, { prop: 'category', label: '服务类型' }, { prop: 'ownerName', label: '护工人员' }, { prop: 'planTime', label: '派单时间' }, { prop: 'status', label: '派单状态' }, { prop: 'remark', label: '派单说明' }]
const formFields = [{ prop: 'recordNo', label: '派单编号' }, { prop: 'recordName', label: '预约订单' }, { prop: 'category', label: '服务类型' }, { prop: 'ownerName', label: '护工人员' }, { prop: 'planTime', label: '派单时间' }, { prop: 'status', label: '派单状态', type: 'select' }, { prop: 'remark', label: '派单说明', type: 'textarea' }]
</script>

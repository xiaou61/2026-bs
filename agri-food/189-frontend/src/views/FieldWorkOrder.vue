<template><DataPage title="作业派单" description="派单编号、作业地块、作业类型、机手人员、派单时间和派单状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'STATION', 'DISPATCH', 'DRIVER', 'FARMER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addFieldWorkOrder, deleteFieldWorkOrder, finishFieldWorkOrder, getFieldWorkOrderPage, processFieldWorkOrder, updateFieldWorkOrder } from '../api'
const api = { page: getFieldWorkOrderPage, add: addFieldWorkOrder, update: updateFieldWorkOrder, delete: deleteFieldWorkOrder, process: processFieldWorkOrder, finish: finishFieldWorkOrder }
const columns = [{ prop: 'recordNo', label: '派单编号' }, { prop: 'recordName', label: '作业地块' }, { prop: 'category', label: '作业类型' }, { prop: 'ownerName', label: '机手人员' }, { prop: 'planTime', label: '派单时间' }, { prop: 'status', label: '派单状态' }, { prop: 'remark', label: '派单说明' }]
const formFields = [{ prop: 'recordNo', label: '派单编号' }, { prop: 'recordName', label: '作业地块' }, { prop: 'category', label: '作业类型' }, { prop: 'ownerName', label: '机手人员' }, { prop: 'planTime', label: '派单时间' }, { prop: 'status', label: '派单状态', type: 'select' }, { prop: 'remark', label: '派单说明', type: 'textarea' }]
</script>

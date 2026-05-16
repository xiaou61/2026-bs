<template><DataPage title="检修回执" description="回执编号、检修阀门、检修类型、检修人员、回执时间和检修状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'DISPATCH', 'REPAIR', 'SUPERVISOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addMaintenanceReceipt, deleteMaintenanceReceipt, finishMaintenanceReceipt, getMaintenanceReceiptPage, processMaintenanceReceipt, updateMaintenanceReceipt } from '../api'
const api = { page: getMaintenanceReceiptPage, add: addMaintenanceReceipt, update: updateMaintenanceReceipt, delete: deleteMaintenanceReceipt, process: processMaintenanceReceipt, finish: finishMaintenanceReceipt }
const columns = [{ prop: 'recordNo', label: '回执编号' }, { prop: 'recordName', label: '检修阀门' }, { prop: 'category', label: '检修类型' }, { prop: 'ownerName', label: '检修人员' }, { prop: 'planTime', label: '回执时间' }, { prop: 'status', label: '检修状态' }, { prop: 'remark', label: '回执说明' }]
const formFields = [{ prop: 'recordNo', label: '回执编号' }, { prop: 'recordName', label: '检修阀门' }, { prop: 'category', label: '检修类型' }, { prop: 'ownerName', label: '检修人员' }, { prop: 'planTime', label: '回执时间' }, { prop: 'status', label: '检修状态', type: 'select' }, { prop: 'remark', label: '回执说明', type: 'textarea' }]
</script>

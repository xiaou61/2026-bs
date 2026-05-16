<template><DataPage title="扫码核销" description="核销码、关联订单、核销渠道、核销员、核销时间和核销状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'MANAGER', 'CHECKER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addTicketVerification, deleteTicketVerification, finishTicketVerification, getTicketVerificationPage, processTicketVerification, updateTicketVerification } from '../api'
const api = { page: getTicketVerificationPage, add: addTicketVerification, update: updateTicketVerification, delete: deleteTicketVerification, process: processTicketVerification, finish: finishTicketVerification }
const columns = [{ prop: 'recordNo', label: '核销码' }, { prop: 'recordName', label: '关联订单' }, { prop: 'category', label: '核销渠道' }, { prop: 'ownerName', label: '核销员' }, { prop: 'planTime', label: '核销时间' }, { prop: 'status', label: '核销状态' }, { prop: 'remark', label: '核销说明' }]
const formFields = [{ prop: 'recordNo', label: '核销码' }, { prop: 'recordName', label: '关联订单' }, { prop: 'category', label: '核销渠道' }, { prop: 'ownerName', label: '核销员' }, { prop: 'planTime', label: '核销时间' }, { prop: 'status', label: '核销状态', type: 'select' }, { prop: 'remark', label: '核销说明', type: 'textarea' }]
</script>

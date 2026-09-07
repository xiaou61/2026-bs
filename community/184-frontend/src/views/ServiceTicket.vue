<template><DataPage title="客服工单" description="工单编号、月租车主、诉求类型、客服人员、提交时间和工单状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'PARKING', 'TENANT', 'SERVICE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addServiceTicket, deleteServiceTicket, finishServiceTicket, getServiceTicketPage, processServiceTicket, updateServiceTicket } from '../api'
const api = { page: getServiceTicketPage, add: addServiceTicket, update: updateServiceTicket, delete: deleteServiceTicket, process: processServiceTicket, finish: finishServiceTicket }
const columns = [{ prop: 'recordNo', label: '工单编号' }, { prop: 'recordName', label: '月租车主' }, { prop: 'category', label: '诉求类型' }, { prop: 'ownerName', label: '客服人员' }, { prop: 'planTime', label: '提交时间' }, { prop: 'status', label: '工单状态' }, { prop: 'remark', label: '工单说明' }]
const formFields = [{ prop: 'recordNo', label: '工单编号' }, { prop: 'recordName', label: '月租车主' }, { prop: 'category', label: '诉求类型' }, { prop: 'ownerName', label: '客服人员' }, { prop: 'planTime', label: '提交时间' }, { prop: 'status', label: '工单状态', type: 'select' }, { prop: 'remark', label: '工单说明', type: 'textarea' }]
</script>

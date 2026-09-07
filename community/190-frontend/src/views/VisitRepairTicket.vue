<template><DataPage title="访修工单" description="工单编号、报修位置、访修类型、报修人员、提交时间和工单状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'PROPERTY', 'DISPATCH', 'TECHNICIAN', 'TENANT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addVisitRepairTicket, deleteVisitRepairTicket, finishVisitRepairTicket, getVisitRepairTicketPage, processVisitRepairTicket, updateVisitRepairTicket } from '../api'
const api = { page: getVisitRepairTicketPage, add: addVisitRepairTicket, update: updateVisitRepairTicket, delete: deleteVisitRepairTicket, process: processVisitRepairTicket, finish: finishVisitRepairTicket }
const columns = [{ prop: 'recordNo', label: '工单编号' }, { prop: 'recordName', label: '报修位置' }, { prop: 'category', label: '访修类型' }, { prop: 'ownerName', label: '报修人员' }, { prop: 'planTime', label: '提交时间' }, { prop: 'status', label: '工单状态' }, { prop: 'remark', label: '工单说明' }]
const formFields = [{ prop: 'recordNo', label: '工单编号' }, { prop: 'recordName', label: '报修位置' }, { prop: 'category', label: '访修类型' }, { prop: 'ownerName', label: '报修人员' }, { prop: 'planTime', label: '提交时间' }, { prop: 'status', label: '工单状态', type: 'select' }, { prop: 'remark', label: '工单说明', type: 'textarea' }]
</script>

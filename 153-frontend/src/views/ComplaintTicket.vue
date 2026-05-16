<template><DataPage title="纠纷申诉" description="申诉编号、关联订单、申诉类型、申诉人、提交时间和处理状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'OPERATOR', 'ARBITER', 'SELLER', 'BUYER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addComplaintTicket, deleteComplaintTicket, finishComplaintTicket, getComplaintTicketPage, processComplaintTicket, updateComplaintTicket } from '../api'
const api = { page: getComplaintTicketPage, add: addComplaintTicket, update: updateComplaintTicket, delete: deleteComplaintTicket, process: processComplaintTicket, finish: finishComplaintTicket }
const columns = [{ prop: 'recordNo', label: '申诉编号' }, { prop: 'recordName', label: '关联订单' }, { prop: 'category', label: '申诉类型' }, { prop: 'ownerName', label: '申诉人' }, { prop: 'planTime', label: '提交时间' }, { prop: 'status', label: '处理状态' }, { prop: 'remark', label: '申诉内容' }]
const formFields = [{ prop: 'recordNo', label: '申诉编号' }, { prop: 'recordName', label: '关联订单' }, { prop: 'category', label: '申诉类型' }, { prop: 'ownerName', label: '申诉人' }, { prop: 'planTime', label: '提交时间' }, { prop: 'status', label: '处理状态', type: 'select' }, { prop: 'remark', label: '申诉内容', type: 'textarea' }]
</script>

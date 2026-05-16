<template><DataPage title="票务预约" description="预约单号、参观场馆、票种类型、预约人、参观时间和预约状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'MANAGER', 'CHECKER', 'VISITOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addTicketOrder, deleteTicketOrder, finishTicketOrder, getTicketOrderPage, processTicketOrder, updateTicketOrder } from '../api'
const api = { page: getTicketOrderPage, add: addTicketOrder, update: updateTicketOrder, delete: deleteTicketOrder, process: processTicketOrder, finish: finishTicketOrder }
const columns = [{ prop: 'recordNo', label: '预约单号' }, { prop: 'recordName', label: '参观场馆' }, { prop: 'category', label: '票种类型' }, { prop: 'ownerName', label: '预约人' }, { prop: 'planTime', label: '参观时间' }, { prop: 'status', label: '预约状态' }, { prop: 'remark', label: '预约备注' }]
const formFields = [{ prop: 'recordNo', label: '预约单号' }, { prop: 'recordName', label: '参观场馆' }, { prop: 'category', label: '票种类型' }, { prop: 'ownerName', label: '预约人' }, { prop: 'planTime', label: '参观时间' }, { prop: 'status', label: '预约状态', type: 'select' }, { prop: 'remark', label: '预约备注', type: 'textarea' }]
</script>

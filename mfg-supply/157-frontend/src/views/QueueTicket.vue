<template><DataPage title="排队叫号" description="叫号编号、排队车辆、作业类型、叫号人、叫号时间和排队状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'DISPATCHER', 'GATEKEEPER', 'YARDMASTER', 'CARRIER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addQueueTicket, deleteQueueTicket, finishQueueTicket, getQueueTicketPage, processQueueTicket, updateQueueTicket } from '../api'
const api = { page: getQueueTicketPage, add: addQueueTicket, update: updateQueueTicket, delete: deleteQueueTicket, process: processQueueTicket, finish: finishQueueTicket }
const columns = [{ prop: 'recordNo', label: '叫号编号' }, { prop: 'recordName', label: '排队车辆' }, { prop: 'category', label: '作业类型' }, { prop: 'ownerName', label: '叫号人' }, { prop: 'planTime', label: '叫号时间' }, { prop: 'status', label: '排队状态' }, { prop: 'remark', label: '叫号说明' }]
const formFields = [{ prop: 'recordNo', label: '叫号编号' }, { prop: 'recordName', label: '排队车辆' }, { prop: 'category', label: '作业类型' }, { prop: 'ownerName', label: '叫号人' }, { prop: 'planTime', label: '叫号时间' }, { prop: 'status', label: '排队状态', type: 'select' }, { prop: 'remark', label: '叫号说明', type: 'textarea' }]
</script>

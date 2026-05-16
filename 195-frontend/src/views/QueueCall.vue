<template><DataPage title="窗口叫号" description="叫号编号、预约事项、叫号类型、窗口人员、叫号时间和叫号状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CENTER', 'WINDOW', 'CITIZEN']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addQueueCall, deleteQueueCall, finishQueueCall, getQueueCallPage, processQueueCall, updateQueueCall } from '../api'
const api = { page: getQueueCallPage, add: addQueueCall, update: updateQueueCall, delete: deleteQueueCall, process: processQueueCall, finish: finishQueueCall }
const columns = [{ prop: 'recordNo', label: '叫号编号' }, { prop: 'recordName', label: '预约事项' }, { prop: 'category', label: '叫号类型' }, { prop: 'ownerName', label: '窗口人员' }, { prop: 'planTime', label: '叫号时间' }, { prop: 'status', label: '叫号状态' }, { prop: 'remark', label: '叫号说明' }]
const formFields = [{ prop: 'recordNo', label: '叫号编号' }, { prop: 'recordName', label: '预约事项' }, { prop: 'category', label: '叫号类型' }, { prop: 'ownerName', label: '窗口人员' }, { prop: 'planTime', label: '叫号时间' }, { prop: 'status', label: '叫号状态', type: 'select' }, { prop: 'remark', label: '叫号说明', type: 'textarea' }]
</script>

<template><DataPage title="归还交接" description="交接编号、交接物品、交接类型、交接人、交接时间和交接状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'SERVICE', 'VISITOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addHandoverRecord, deleteHandoverRecord, finishHandoverRecord, getHandoverRecordPage, processHandoverRecord, updateHandoverRecord } from '../api'
const api = { page: getHandoverRecordPage, add: addHandoverRecord, update: updateHandoverRecord, delete: deleteHandoverRecord, process: processHandoverRecord, finish: finishHandoverRecord }
const columns = [{ prop: 'recordNo', label: '交接编号' }, { prop: 'recordName', label: '交接物品' }, { prop: 'category', label: '交接类型' }, { prop: 'ownerName', label: '交接人' }, { prop: 'planTime', label: '交接时间' }, { prop: 'status', label: '交接状态' }, { prop: 'remark', label: '交接说明' }]
const formFields = [{ prop: 'recordNo', label: '交接编号' }, { prop: 'recordName', label: '交接物品' }, { prop: 'category', label: '交接类型' }, { prop: 'ownerName', label: '交接人' }, { prop: 'planTime', label: '交接时间' }, { prop: 'status', label: '交接状态', type: 'select' }, { prop: 'remark', label: '交接说明', type: 'textarea' }]
</script>

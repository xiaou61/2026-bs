<template><DataPage title="上架审核" description="审核编号、寄卖物品、审核类型、审核人、审核时间和审核状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'OPERATOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addItemAudit, deleteItemAudit, finishItemAudit, getItemAuditPage, processItemAudit, updateItemAudit } from '../api'
const api = { page: getItemAuditPage, add: addItemAudit, update: updateItemAudit, delete: deleteItemAudit, process: processItemAudit, finish: finishItemAudit }
const columns = [{ prop: 'recordNo', label: '审核编号' }, { prop: 'recordName', label: '寄卖物品' }, { prop: 'category', label: '审核类型' }, { prop: 'ownerName', label: '审核人' }, { prop: 'planTime', label: '审核时间' }, { prop: 'status', label: '审核状态' }, { prop: 'remark', label: '审核意见' }]
const formFields = [{ prop: 'recordNo', label: '审核编号' }, { prop: 'recordName', label: '寄卖物品' }, { prop: 'category', label: '审核类型' }, { prop: 'ownerName', label: '审核人' }, { prop: 'planTime', label: '审核时间' }, { prop: 'status', label: '审核状态', type: 'select' }, { prop: 'remark', label: '审核意见', type: 'textarea' }]
</script>

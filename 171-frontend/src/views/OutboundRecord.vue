<template><DataPage title="出库记录" description="出库编号、出库物资、出库类型、仓储人员、出库时间和出库状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'WAREHOUSE', 'DISPATCH', 'AUDITOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addOutboundRecord, deleteOutboundRecord, finishOutboundRecord, getOutboundRecordPage, processOutboundRecord, updateOutboundRecord } from '../api'
const api = { page: getOutboundRecordPage, add: addOutboundRecord, update: updateOutboundRecord, delete: deleteOutboundRecord, process: processOutboundRecord, finish: finishOutboundRecord }
const columns = [{ prop: 'recordNo', label: '出库编号' }, { prop: 'recordName', label: '出库物资' }, { prop: 'category', label: '出库类型' }, { prop: 'ownerName', label: '仓储人员' }, { prop: 'planTime', label: '出库时间' }, { prop: 'status', label: '出库状态' }, { prop: 'remark', label: '出库说明' }]
const formFields = [{ prop: 'recordNo', label: '出库编号' }, { prop: 'recordName', label: '出库物资' }, { prop: 'category', label: '出库类型' }, { prop: 'ownerName', label: '仓储人员' }, { prop: 'planTime', label: '出库时间' }, { prop: 'status', label: '出库状态', type: 'select' }, { prop: 'remark', label: '出库说明', type: 'textarea' }]
</script>

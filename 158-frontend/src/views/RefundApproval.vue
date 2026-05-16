<template><DataPage title="退费审批" description="审批编号、退费申请、审批类型、审批人、审批时间和审批状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'PRINCIPAL', 'FINANCE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addRefundApproval, deleteRefundApproval, finishRefundApproval, getRefundApprovalPage, processRefundApproval, updateRefundApproval } from '../api'
const api = { page: getRefundApprovalPage, add: addRefundApproval, update: updateRefundApproval, delete: deleteRefundApproval, process: processRefundApproval, finish: finishRefundApproval }
const columns = [{ prop: 'recordNo', label: '审批编号' }, { prop: 'recordName', label: '退费申请' }, { prop: 'category', label: '审批类型' }, { prop: 'ownerName', label: '审批人' }, { prop: 'planTime', label: '审批时间' }, { prop: 'status', label: '审批状态' }, { prop: 'remark', label: '审批说明' }]
const formFields = [{ prop: 'recordNo', label: '审批编号' }, { prop: 'recordName', label: '退费申请' }, { prop: 'category', label: '审批类型' }, { prop: 'ownerName', label: '审批人' }, { prop: 'planTime', label: '审批时间' }, { prop: 'status', label: '审批状态', type: 'select' }, { prop: 'remark', label: '审批说明', type: 'textarea' }]
</script>

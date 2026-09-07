<template><DataPage title="审批记录" description="审批编号、审批事项、审批类型、审批人、审批时间和审批状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'UNION']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addApprovalRecord, deleteApprovalRecord, finishApprovalRecord, getApprovalRecordPage, processApprovalRecord, updateApprovalRecord } from '../api'
const api = { page: getApprovalRecordPage, add: addApprovalRecord, update: updateApprovalRecord, delete: deleteApprovalRecord, process: processApprovalRecord, finish: finishApprovalRecord }
const columns = [{ prop: 'recordNo', label: '审批编号' }, { prop: 'recordName', label: '审批事项' }, { prop: 'category', label: '审批类型' }, { prop: 'ownerName', label: '审批人' }, { prop: 'planTime', label: '审批时间' }, { prop: 'status', label: '审批状态' }, { prop: 'remark', label: '审批说明' }]
const formFields = [{ prop: 'recordNo', label: '审批编号' }, { prop: 'recordName', label: '审批事项' }, { prop: 'category', label: '审批类型' }, { prop: 'ownerName', label: '审批人' }, { prop: 'planTime', label: '审批时间' }, { prop: 'status', label: '审批状态', type: 'select' }, { prop: 'remark', label: '审批说明', type: 'textarea' }]
</script>

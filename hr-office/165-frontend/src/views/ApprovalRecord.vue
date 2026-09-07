<template><DataPage title="访问审批" description="审批编号、预约事项、审批类型、审批人员、审批时间和审批状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'RECEPTION', 'EMPLOYEE', 'MANAGER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addApprovalRecord, deleteApprovalRecord, finishApprovalRecord, getApprovalRecordPage, processApprovalRecord, updateApprovalRecord } from '../api'
const api = { page: getApprovalRecordPage, add: addApprovalRecord, update: updateApprovalRecord, delete: deleteApprovalRecord, process: processApprovalRecord, finish: finishApprovalRecord }
const columns = [{ prop: 'recordNo', label: '审批编号' }, { prop: 'recordName', label: '预约事项' }, { prop: 'category', label: '审批类型' }, { prop: 'ownerName', label: '审批人员' }, { prop: 'planTime', label: '审批时间' }, { prop: 'status', label: '审批状态' }, { prop: 'remark', label: '审批说明' }]
const formFields = [{ prop: 'recordNo', label: '审批编号' }, { prop: 'recordName', label: '预约事项' }, { prop: 'category', label: '审批类型' }, { prop: 'ownerName', label: '审批人员' }, { prop: 'planTime', label: '审批时间' }, { prop: 'status', label: '审批状态', type: 'select' }, { prop: 'remark', label: '审批说明', type: 'textarea' }]
</script>

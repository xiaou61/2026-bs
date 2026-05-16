<template><DataPage title="调拨审批" description="审批编号、调拨物资、审批类型、审核人员、审批时间和审批状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'DISPATCH', 'AUDITOR', 'WAREHOUSE', 'APPLICANT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addAllocationApproval, deleteAllocationApproval, finishAllocationApproval, getAllocationApprovalPage, processAllocationApproval, updateAllocationApproval } from '../api'
const api = { page: getAllocationApprovalPage, add: addAllocationApproval, update: updateAllocationApproval, delete: deleteAllocationApproval, process: processAllocationApproval, finish: finishAllocationApproval }
const columns = [{ prop: 'recordNo', label: '审批编号' }, { prop: 'recordName', label: '调拨物资' }, { prop: 'category', label: '审批类型' }, { prop: 'ownerName', label: '审核人员' }, { prop: 'planTime', label: '审批时间' }, { prop: 'status', label: '审批状态' }, { prop: 'remark', label: '审批说明' }]
const formFields = [{ prop: 'recordNo', label: '审批编号' }, { prop: 'recordName', label: '调拨物资' }, { prop: 'category', label: '审批类型' }, { prop: 'ownerName', label: '审核人员' }, { prop: 'planTime', label: '审批时间' }, { prop: 'status', label: '审批状态', type: 'select' }, { prop: 'remark', label: '审批说明', type: 'textarea' }]
</script>

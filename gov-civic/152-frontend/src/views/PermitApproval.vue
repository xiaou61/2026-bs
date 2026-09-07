<template><DataPage title="审批链路" description="审批编号、作业票号、审批节点、审批人、审批时间和审批状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'SAFETY', 'APPROVER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addPermitApproval, deletePermitApproval, finishPermitApproval, getPermitApprovalPage, processPermitApproval, updatePermitApproval } from '../api'
const api = { page: getPermitApprovalPage, add: addPermitApproval, update: updatePermitApproval, delete: deletePermitApproval, process: processPermitApproval, finish: finishPermitApproval }
const columns = [{ prop: 'recordNo', label: '审批编号' }, { prop: 'recordName', label: '作业票号' }, { prop: 'category', label: '审批节点' }, { prop: 'ownerName', label: '审批人' }, { prop: 'planTime', label: '审批时间' }, { prop: 'status', label: '审批状态' }, { prop: 'remark', label: '审批意见' }]
const formFields = [{ prop: 'recordNo', label: '审批编号' }, { prop: 'recordName', label: '作业票号' }, { prop: 'category', label: '审批节点' }, { prop: 'ownerName', label: '审批人' }, { prop: 'planTime', label: '审批时间' }, { prop: 'status', label: '审批状态', type: 'select' }, { prop: 'remark', label: '审批意见', type: 'textarea' }]
</script>

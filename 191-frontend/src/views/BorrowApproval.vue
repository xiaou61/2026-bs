<template><DataPage title="借用审核" description="审核编号、借用申请、审核类型、审核人员、审核时间和审核状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'COMMUNITY', 'AIDSTAFF']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addBorrowApproval, deleteBorrowApproval, finishBorrowApproval, getBorrowApprovalPage, processBorrowApproval, updateBorrowApproval } from '../api'
const api = { page: getBorrowApprovalPage, add: addBorrowApproval, update: updateBorrowApproval, delete: deleteBorrowApproval, process: processBorrowApproval, finish: finishBorrowApproval }
const columns = [{ prop: 'recordNo', label: '审核编号' }, { prop: 'recordName', label: '借用申请' }, { prop: 'category', label: '审核类型' }, { prop: 'ownerName', label: '审核人员' }, { prop: 'planTime', label: '审核时间' }, { prop: 'status', label: '审核状态' }, { prop: 'remark', label: '审核说明' }]
const formFields = [{ prop: 'recordNo', label: '审核编号' }, { prop: 'recordName', label: '借用申请' }, { prop: 'category', label: '审核类型' }, { prop: 'ownerName', label: '审核人员' }, { prop: 'planTime', label: '审核时间' }, { prop: 'status', label: '审核状态', type: 'select' }, { prop: 'remark', label: '审核说明', type: 'textarea' }]
</script>

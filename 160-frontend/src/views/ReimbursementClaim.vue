<template><DataPage title="报销申请" description="报销编号、报销活动、报销类型、申请人、申请时间和报销状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'UNION', 'CLUB', 'TREASURER', 'MEMBER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addReimbursementClaim, deleteReimbursementClaim, finishReimbursementClaim, getReimbursementClaimPage, processReimbursementClaim, updateReimbursementClaim } from '../api'
const api = { page: getReimbursementClaimPage, add: addReimbursementClaim, update: updateReimbursementClaim, delete: deleteReimbursementClaim, process: processReimbursementClaim, finish: finishReimbursementClaim }
const columns = [{ prop: 'recordNo', label: '报销编号' }, { prop: 'recordName', label: '报销活动' }, { prop: 'category', label: '报销类型' }, { prop: 'ownerName', label: '申请人' }, { prop: 'planTime', label: '申请时间' }, { prop: 'status', label: '报销状态' }, { prop: 'remark', label: '报销说明' }]
const formFields = [{ prop: 'recordNo', label: '报销编号' }, { prop: 'recordName', label: '报销活动' }, { prop: 'category', label: '报销类型' }, { prop: 'ownerName', label: '申请人' }, { prop: 'planTime', label: '申请时间' }, { prop: 'status', label: '报销状态', type: 'select' }, { prop: 'remark', label: '报销说明', type: 'textarea' }]
</script>

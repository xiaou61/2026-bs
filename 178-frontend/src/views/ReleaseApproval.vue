<template><DataPage title="放行审核" description="审核编号、器械包名称、审核类型、质控人员、审核时间和放行状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'QA', 'CSSD', 'ORNURSE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addReleaseApproval, deleteReleaseApproval, finishReleaseApproval, getReleaseApprovalPage, processReleaseApproval, updateReleaseApproval } from '../api'
const api = { page: getReleaseApprovalPage, add: addReleaseApproval, update: updateReleaseApproval, delete: deleteReleaseApproval, process: processReleaseApproval, finish: finishReleaseApproval }
const columns = [{ prop: 'recordNo', label: '审核编号' }, { prop: 'recordName', label: '器械包名称' }, { prop: 'category', label: '审核类型' }, { prop: 'ownerName', label: '质控人员' }, { prop: 'planTime', label: '审核时间' }, { prop: 'status', label: '放行状态' }, { prop: 'remark', label: '审核说明' }]
const formFields = [{ prop: 'recordNo', label: '审核编号' }, { prop: 'recordName', label: '器械包名称' }, { prop: 'category', label: '审核类型' }, { prop: 'ownerName', label: '质控人员' }, { prop: 'planTime', label: '审核时间' }, { prop: 'status', label: '放行状态', type: 'select' }, { prop: 'remark', label: '审核说明', type: 'textarea' }]
</script>

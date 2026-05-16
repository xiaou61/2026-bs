<template><DataPage title="作业票申请" description="作业票号、作业项目、作业类型、申请人、作业时间和申请状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'SAFETY', 'WORKER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addWorkPermit, deleteWorkPermit, finishWorkPermit, getWorkPermitPage, processWorkPermit, updateWorkPermit } from '../api'
const api = { page: getWorkPermitPage, add: addWorkPermit, update: updateWorkPermit, delete: deleteWorkPermit, process: processWorkPermit, finish: finishWorkPermit }
const columns = [{ prop: 'recordNo', label: '作业票号' }, { prop: 'recordName', label: '作业项目' }, { prop: 'category', label: '作业类型' }, { prop: 'ownerName', label: '申请人' }, { prop: 'planTime', label: '作业时间' }, { prop: 'status', label: '申请状态' }, { prop: 'remark', label: '作业说明' }]
const formFields = [{ prop: 'recordNo', label: '作业票号' }, { prop: 'recordName', label: '作业项目' }, { prop: 'category', label: '作业类型' }, { prop: 'ownerName', label: '申请人' }, { prop: 'planTime', label: '作业时间' }, { prop: 'status', label: '申请状态', type: 'select' }, { prop: 'remark', label: '作业说明', type: 'textarea' }]
</script>

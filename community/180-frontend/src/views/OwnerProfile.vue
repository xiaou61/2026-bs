<template><DataPage title="业主档案" description="业主编号、业主姓名、住户类型、服务管家、入住时间和业主状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'PROPERTY', 'OWNER', 'FINANCE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addOwnerProfile, deleteOwnerProfile, finishOwnerProfile, getOwnerProfilePage, processOwnerProfile, updateOwnerProfile } from '../api'
const api = { page: getOwnerProfilePage, add: addOwnerProfile, update: updateOwnerProfile, delete: deleteOwnerProfile, process: processOwnerProfile, finish: finishOwnerProfile }
const columns = [{ prop: 'recordNo', label: '业主编号' }, { prop: 'recordName', label: '业主姓名' }, { prop: 'category', label: '住户类型' }, { prop: 'ownerName', label: '服务管家' }, { prop: 'planTime', label: '入住时间' }, { prop: 'status', label: '业主状态' }, { prop: 'remark', label: '业主说明' }]
const formFields = [{ prop: 'recordNo', label: '业主编号' }, { prop: 'recordName', label: '业主姓名' }, { prop: 'category', label: '住户类型' }, { prop: 'ownerName', label: '服务管家' }, { prop: 'planTime', label: '入住时间' }, { prop: 'status', label: '业主状态', type: 'select' }, { prop: 'remark', label: '业主说明', type: 'textarea' }]
</script>

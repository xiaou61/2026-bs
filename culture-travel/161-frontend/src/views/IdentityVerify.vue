<template><DataPage title="身份核验" description="核验编号、核验对象、核验类型、核验人、核验时间和核验状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'SERVICE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addIdentityVerify, deleteIdentityVerify, finishIdentityVerify, getIdentityVerifyPage, processIdentityVerify, updateIdentityVerify } from '../api'
const api = { page: getIdentityVerifyPage, add: addIdentityVerify, update: updateIdentityVerify, delete: deleteIdentityVerify, process: processIdentityVerify, finish: finishIdentityVerify }
const columns = [{ prop: 'recordNo', label: '核验编号' }, { prop: 'recordName', label: '核验对象' }, { prop: 'category', label: '核验类型' }, { prop: 'ownerName', label: '核验人' }, { prop: 'planTime', label: '核验时间' }, { prop: 'status', label: '核验状态' }, { prop: 'remark', label: '核验说明' }]
const formFields = [{ prop: 'recordNo', label: '核验编号' }, { prop: 'recordName', label: '核验对象' }, { prop: 'category', label: '核验类型' }, { prop: 'ownerName', label: '核验人' }, { prop: 'planTime', label: '核验时间' }, { prop: 'status', label: '核验状态', type: 'select' }, { prop: 'remark', label: '核验说明', type: 'textarea' }]
</script>

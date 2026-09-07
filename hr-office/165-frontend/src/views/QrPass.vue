<template><DataPage title="二维码通行" description="通行码编号、通行对象、通行类型、签发人员、有效时间和通行状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'RECEPTION', 'SECURITY', 'VISITOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addQrPass, deleteQrPass, finishQrPass, getQrPassPage, processQrPass, updateQrPass } from '../api'
const api = { page: getQrPassPage, add: addQrPass, update: updateQrPass, delete: deleteQrPass, process: processQrPass, finish: finishQrPass }
const columns = [{ prop: 'recordNo', label: '通行码编号' }, { prop: 'recordName', label: '通行对象' }, { prop: 'category', label: '通行类型' }, { prop: 'ownerName', label: '签发人员' }, { prop: 'planTime', label: '有效时间' }, { prop: 'status', label: '通行状态' }, { prop: 'remark', label: '通行说明' }]
const formFields = [{ prop: 'recordNo', label: '通行码编号' }, { prop: 'recordName', label: '通行对象' }, { prop: 'category', label: '通行类型' }, { prop: 'ownerName', label: '签发人员' }, { prop: 'planTime', label: '有效时间' }, { prop: 'status', label: '通行状态', type: 'select' }, { prop: 'remark', label: '通行说明', type: 'textarea' }]
</script>

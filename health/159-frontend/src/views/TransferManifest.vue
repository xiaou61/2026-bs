<template><DataPage title="转运联单" description="联单编号、医废批次、转运类型、承运人、转运时间和联单状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'COLLECTOR', 'TRANSPORTER', 'REGULATOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addTransferManifest, deleteTransferManifest, finishTransferManifest, getTransferManifestPage, processTransferManifest, updateTransferManifest } from '../api'
const api = { page: getTransferManifestPage, add: addTransferManifest, update: updateTransferManifest, delete: deleteTransferManifest, process: processTransferManifest, finish: finishTransferManifest }
const columns = [{ prop: 'recordNo', label: '联单编号' }, { prop: 'recordName', label: '医废批次' }, { prop: 'category', label: '转运类型' }, { prop: 'ownerName', label: '承运人' }, { prop: 'planTime', label: '转运时间' }, { prop: 'status', label: '联单状态' }, { prop: 'remark', label: '联单说明' }]
const formFields = [{ prop: 'recordNo', label: '联单编号' }, { prop: 'recordName', label: '医废批次' }, { prop: 'category', label: '转运类型' }, { prop: 'ownerName', label: '承运人' }, { prop: 'planTime', label: '转运时间' }, { prop: 'status', label: '联单状态', type: 'select' }, { prop: 'remark', label: '联单说明', type: 'textarea' }]
</script>

<template><DataPage title="退费申请" description="申请编号、申请学员、退费类型、申请人、申请时间和申请状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'PRINCIPAL', 'FINANCE', 'PARENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addRefundApplication, deleteRefundApplication, finishRefundApplication, getRefundApplicationPage, processRefundApplication, updateRefundApplication } from '../api'
const api = { page: getRefundApplicationPage, add: addRefundApplication, update: updateRefundApplication, delete: deleteRefundApplication, process: processRefundApplication, finish: finishRefundApplication }
const columns = [{ prop: 'recordNo', label: '申请编号' }, { prop: 'recordName', label: '申请学员' }, { prop: 'category', label: '退费类型' }, { prop: 'ownerName', label: '申请人' }, { prop: 'planTime', label: '申请时间' }, { prop: 'status', label: '申请状态' }, { prop: 'remark', label: '申请说明' }]
const formFields = [{ prop: 'recordNo', label: '申请编号' }, { prop: 'recordName', label: '申请学员' }, { prop: 'category', label: '退费类型' }, { prop: 'ownerName', label: '申请人' }, { prop: 'planTime', label: '申请时间' }, { prop: 'status', label: '申请状态', type: 'select' }, { prop: 'remark', label: '申请说明', type: 'textarea' }]
</script>

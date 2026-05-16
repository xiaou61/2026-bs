<template><DataPage title="续证申请" description="续证编号、续证证书、续证类型、申请人员、申请时间和续证状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'TRAINING', 'CERTIFIER', 'TRAINEE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addRenewalApplication, deleteRenewalApplication, finishRenewalApplication, getRenewalApplicationPage, processRenewalApplication, updateRenewalApplication } from '../api'
const api = { page: getRenewalApplicationPage, add: addRenewalApplication, update: updateRenewalApplication, delete: deleteRenewalApplication, process: processRenewalApplication, finish: finishRenewalApplication }
const columns = [{ prop: 'recordNo', label: '续证编号' }, { prop: 'recordName', label: '续证证书' }, { prop: 'category', label: '续证类型' }, { prop: 'ownerName', label: '申请人员' }, { prop: 'planTime', label: '申请时间' }, { prop: 'status', label: '续证状态' }, { prop: 'remark', label: '续证说明' }]
const formFields = [{ prop: 'recordNo', label: '续证编号' }, { prop: 'recordName', label: '续证证书' }, { prop: 'category', label: '续证类型' }, { prop: 'ownerName', label: '申请人员' }, { prop: 'planTime', label: '申请时间' }, { prop: 'status', label: '续证状态', type: 'select' }, { prop: 'remark', label: '续证说明', type: 'textarea' }]
</script>

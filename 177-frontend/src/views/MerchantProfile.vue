<template><DataPage title="商家档案" description="商家编号、商家名称、经营类型、对接人员、入驻时间和商家状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'BASE', 'SELECTOR', 'MERCHANT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addMerchantProfile, deleteMerchantProfile, finishMerchantProfile, getMerchantProfilePage, processMerchantProfile, updateMerchantProfile } from '../api'
const api = { page: getMerchantProfilePage, add: addMerchantProfile, update: updateMerchantProfile, delete: deleteMerchantProfile, process: processMerchantProfile, finish: finishMerchantProfile }
const columns = [{ prop: 'recordNo', label: '商家编号' }, { prop: 'recordName', label: '商家名称' }, { prop: 'category', label: '经营类型' }, { prop: 'ownerName', label: '对接人员' }, { prop: 'planTime', label: '入驻时间' }, { prop: 'status', label: '商家状态' }, { prop: 'remark', label: '商家说明' }]
const formFields = [{ prop: 'recordNo', label: '商家编号' }, { prop: 'recordName', label: '商家名称' }, { prop: 'category', label: '经营类型' }, { prop: 'ownerName', label: '对接人员' }, { prop: 'planTime', label: '入驻时间' }, { prop: 'status', label: '商家状态', type: 'select' }, { prop: 'remark', label: '商家说明', type: 'textarea' }]
</script>

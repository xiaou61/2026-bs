<template><DataPage title="商户收益" description="收益编号、合作点位、收益类型、商户负责人、入账时间和收益状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'OPERATOR', 'FINANCE', 'MERCHANT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addMerchantIncome, deleteMerchantIncome, finishMerchantIncome, getMerchantIncomePage, processMerchantIncome, updateMerchantIncome } from '../api'
const api = { page: getMerchantIncomePage, add: addMerchantIncome, update: updateMerchantIncome, delete: deleteMerchantIncome, process: processMerchantIncome, finish: finishMerchantIncome }
const columns = [{ prop: 'recordNo', label: '收益编号' }, { prop: 'recordName', label: '合作点位' }, { prop: 'category', label: '收益类型' }, { prop: 'ownerName', label: '商户负责人' }, { prop: 'planTime', label: '入账时间' }, { prop: 'status', label: '收益状态' }, { prop: 'remark', label: '收益说明' }]
const formFields = [{ prop: 'recordNo', label: '收益编号' }, { prop: 'recordName', label: '合作点位' }, { prop: 'category', label: '收益类型' }, { prop: 'ownerName', label: '商户负责人' }, { prop: 'planTime', label: '入账时间' }, { prop: 'status', label: '收益状态', type: 'select' }, { prop: 'remark', label: '收益说明', type: 'textarea' }]
</script>

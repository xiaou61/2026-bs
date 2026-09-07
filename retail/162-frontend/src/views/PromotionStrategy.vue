<template><DataPage title="促销策略" description="策略编号、促销商品、促销类型、促销专员、开始时间和策略状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'MANAGER', 'MARKETING']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addPromotionStrategy, deletePromotionStrategy, finishPromotionStrategy, getPromotionStrategyPage, processPromotionStrategy, updatePromotionStrategy } from '../api'
const api = { page: getPromotionStrategyPage, add: addPromotionStrategy, update: updatePromotionStrategy, delete: deletePromotionStrategy, process: processPromotionStrategy, finish: finishPromotionStrategy }
const columns = [{ prop: 'recordNo', label: '策略编号' }, { prop: 'recordName', label: '促销商品' }, { prop: 'category', label: '促销类型' }, { prop: 'ownerName', label: '促销专员' }, { prop: 'planTime', label: '开始时间' }, { prop: 'status', label: '策略状态' }, { prop: 'remark', label: '策略说明' }]
const formFields = [{ prop: 'recordNo', label: '策略编号' }, { prop: 'recordName', label: '促销商品' }, { prop: 'category', label: '促销类型' }, { prop: 'ownerName', label: '促销专员' }, { prop: 'planTime', label: '开始时间' }, { prop: 'status', label: '策略状态', type: 'select' }, { prop: 'remark', label: '策略说明', type: 'textarea' }]
</script>

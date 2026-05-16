<template><DataPage title="兑换商品" description="商品编号、商品名称、商品类型、兑换负责人、上架时间和商品状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'COMMUNITY', 'EXCHANGE', 'RESIDENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addRewardCatalog, deleteRewardCatalog, finishRewardCatalog, getRewardCatalogPage, processRewardCatalog, updateRewardCatalog } from '../api'
const api = { page: getRewardCatalogPage, add: addRewardCatalog, update: updateRewardCatalog, delete: deleteRewardCatalog, process: processRewardCatalog, finish: finishRewardCatalog }
const columns = [{ prop: 'recordNo', label: '商品编号' }, { prop: 'recordName', label: '商品名称' }, { prop: 'category', label: '商品类型' }, { prop: 'ownerName', label: '兑换负责人' }, { prop: 'planTime', label: '上架时间' }, { prop: 'status', label: '商品状态' }, { prop: 'remark', label: '商品说明' }]
const formFields = [{ prop: 'recordNo', label: '商品编号' }, { prop: 'recordName', label: '商品名称' }, { prop: 'category', label: '商品类型' }, { prop: 'ownerName', label: '兑换负责人' }, { prop: 'planTime', label: '上架时间' }, { prop: 'status', label: '商品状态', type: 'select' }, { prop: 'remark', label: '商品说明', type: 'textarea' }]
</script>

<template><DataPage title="商品溯源" description="溯源码、商品名称、商品类别、供货商、入场时间和溯源状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'MARKET', 'INSPECTOR', 'VENDOR', 'REGULATOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addProductTrace, deleteProductTrace, finishProductTrace, getProductTracePage, processProductTrace, updateProductTrace } from '../api'
const api = { page: getProductTracePage, add: addProductTrace, update: updateProductTrace, delete: deleteProductTrace, process: processProductTrace, finish: finishProductTrace }
const columns = [{ prop: 'recordNo', label: '溯源码' }, { prop: 'recordName', label: '商品名称' }, { prop: 'category', label: '商品类别' }, { prop: 'ownerName', label: '供货商' }, { prop: 'planTime', label: '入场时间' }, { prop: 'status', label: '溯源状态' }, { prop: 'remark', label: '溯源说明' }]
const formFields = [{ prop: 'recordNo', label: '溯源码' }, { prop: 'recordName', label: '商品名称' }, { prop: 'category', label: '商品类别' }, { prop: 'ownerName', label: '供货商' }, { prop: 'planTime', label: '入场时间' }, { prop: 'status', label: '溯源状态', type: 'select' }, { prop: 'remark', label: '溯源说明', type: 'textarea' }]
</script>

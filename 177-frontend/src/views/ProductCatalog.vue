<template><DataPage title="商品选品" description="商品编号、商品名称、商品类型、提报商家、提报时间和选品状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'BASE', 'SELECTOR', 'MERCHANT', 'ANCHOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addProductCatalog, deleteProductCatalog, finishProductCatalog, getProductCatalogPage, processProductCatalog, updateProductCatalog } from '../api'
const api = { page: getProductCatalogPage, add: addProductCatalog, update: updateProductCatalog, delete: deleteProductCatalog, process: processProductCatalog, finish: finishProductCatalog }
const columns = [{ prop: 'recordNo', label: '商品编号' }, { prop: 'recordName', label: '商品名称' }, { prop: 'category', label: '商品类型' }, { prop: 'ownerName', label: '提报商家' }, { prop: 'planTime', label: '提报时间' }, { prop: 'status', label: '选品状态' }, { prop: 'remark', label: '商品说明' }]
const formFields = [{ prop: 'recordNo', label: '商品编号' }, { prop: 'recordName', label: '商品名称' }, { prop: 'category', label: '商品类型' }, { prop: 'ownerName', label: '提报商家' }, { prop: 'planTime', label: '提报时间' }, { prop: 'status', label: '选品状态', type: 'select' }, { prop: 'remark', label: '商品说明', type: 'textarea' }]
</script>

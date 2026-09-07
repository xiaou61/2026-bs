<template><DataPage title="商品批次" description="批次编号、商品名称、商品品类、理货员、到货时间和批次状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'MANAGER', 'CLERK', 'STOCK']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addProductBatch, deleteProductBatch, finishProductBatch, getProductBatchPage, processProductBatch, updateProductBatch } from '../api'
const api = { page: getProductBatchPage, add: addProductBatch, update: updateProductBatch, delete: deleteProductBatch, process: processProductBatch, finish: finishProductBatch }
const columns = [{ prop: 'recordNo', label: '批次编号' }, { prop: 'recordName', label: '商品名称' }, { prop: 'category', label: '商品品类' }, { prop: 'ownerName', label: '理货员' }, { prop: 'planTime', label: '到货时间' }, { prop: 'status', label: '批次状态' }, { prop: 'remark', label: '批次说明' }]
const formFields = [{ prop: 'recordNo', label: '批次编号' }, { prop: 'recordName', label: '商品名称' }, { prop: 'category', label: '商品品类' }, { prop: 'ownerName', label: '理货员' }, { prop: 'planTime', label: '到货时间' }, { prop: 'status', label: '批次状态', type: 'select' }, { prop: 'remark', label: '批次说明', type: 'textarea' }]
</script>

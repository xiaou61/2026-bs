<template><DataPage title="耗材库存" description="库存编号、耗材名称、耗材类型、保管人员、入库时间和库存状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'SANITATION', 'SUPPLY', 'CLEANER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addConsumableStock, deleteConsumableStock, finishConsumableStock, getConsumableStockPage, processConsumableStock, updateConsumableStock } from '../api'
const api = { page: getConsumableStockPage, add: addConsumableStock, update: updateConsumableStock, delete: deleteConsumableStock, process: processConsumableStock, finish: finishConsumableStock }
const columns = [{ prop: 'recordNo', label: '库存编号' }, { prop: 'recordName', label: '耗材名称' }, { prop: 'category', label: '耗材类型' }, { prop: 'ownerName', label: '保管人员' }, { prop: 'planTime', label: '入库时间' }, { prop: 'status', label: '库存状态' }, { prop: 'remark', label: '库存说明' }]
const formFields = [{ prop: 'recordNo', label: '库存编号' }, { prop: 'recordName', label: '耗材名称' }, { prop: 'category', label: '耗材类型' }, { prop: 'ownerName', label: '保管人员' }, { prop: 'planTime', label: '入库时间' }, { prop: 'status', label: '库存状态', type: 'select' }, { prop: 'remark', label: '库存说明', type: 'textarea' }]
</script>

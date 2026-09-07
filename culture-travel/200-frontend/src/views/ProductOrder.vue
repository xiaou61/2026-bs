<template><DataPage title="展销订单" description="订单编号、作品名称、订单类型、下单用户、下单时间和订单状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'WORKSHOP', 'SALES', 'VISITOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addProductOrder, deleteProductOrder, finishProductOrder, getProductOrderPage, processProductOrder, updateProductOrder } from '../api'
const api = { page: getProductOrderPage, add: addProductOrder, update: updateProductOrder, delete: deleteProductOrder, process: processProductOrder, finish: finishProductOrder }
const columns = [{ prop: 'recordNo', label: '订单编号' }, { prop: 'recordName', label: '作品名称' }, { prop: 'category', label: '订单类型' }, { prop: 'ownerName', label: '下单用户' }, { prop: 'planTime', label: '下单时间' }, { prop: 'status', label: '订单状态' }, { prop: 'remark', label: '订单说明' }]
const formFields = [{ prop: 'recordNo', label: '订单编号' }, { prop: 'recordName', label: '作品名称' }, { prop: 'category', label: '订单类型' }, { prop: 'ownerName', label: '下单用户' }, { prop: 'planTime', label: '下单时间' }, { prop: 'status', label: '订单状态', type: 'select' }, { prop: 'remark', label: '订单说明', type: 'textarea' }]
</script>

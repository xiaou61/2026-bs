<template><DataPage title="备件库存" description="备件编号、备件名称、备件类型、库管人员、入库时间和库存状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'PROPERTY', 'TECHNICIAN']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addSparePartStock, deleteSparePartStock, finishSparePartStock, getSparePartStockPage, processSparePartStock, updateSparePartStock } from '../api'
const api = { page: getSparePartStockPage, add: addSparePartStock, update: updateSparePartStock, delete: deleteSparePartStock, process: processSparePartStock, finish: finishSparePartStock }
const columns = [{ prop: 'recordNo', label: '备件编号' }, { prop: 'recordName', label: '备件名称' }, { prop: 'category', label: '备件类型' }, { prop: 'ownerName', label: '库管人员' }, { prop: 'planTime', label: '入库时间' }, { prop: 'status', label: '库存状态' }, { prop: 'remark', label: '备件说明' }]
const formFields = [{ prop: 'recordNo', label: '备件编号' }, { prop: 'recordName', label: '备件名称' }, { prop: 'category', label: '备件类型' }, { prop: 'ownerName', label: '库管人员' }, { prop: 'planTime', label: '入库时间' }, { prop: 'status', label: '库存状态', type: 'select' }, { prop: 'remark', label: '备件说明', type: 'textarea' }]
</script>

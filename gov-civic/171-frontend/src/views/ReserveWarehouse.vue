<template><DataPage title="储备仓库" description="仓库编号、仓库名称、仓库类型、负责人员、启用时间和仓库状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'WAREHOUSE', 'DISPATCH', 'AUDITOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addReserveWarehouse, deleteReserveWarehouse, finishReserveWarehouse, getReserveWarehousePage, processReserveWarehouse, updateReserveWarehouse } from '../api'
const api = { page: getReserveWarehousePage, add: addReserveWarehouse, update: updateReserveWarehouse, delete: deleteReserveWarehouse, process: processReserveWarehouse, finish: finishReserveWarehouse }
const columns = [{ prop: 'recordNo', label: '仓库编号' }, { prop: 'recordName', label: '仓库名称' }, { prop: 'category', label: '仓库类型' }, { prop: 'ownerName', label: '负责人员' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '仓库状态' }, { prop: 'remark', label: '仓库说明' }]
const formFields = [{ prop: 'recordNo', label: '仓库编号' }, { prop: 'recordName', label: '仓库名称' }, { prop: 'category', label: '仓库类型' }, { prop: 'ownerName', label: '负责人员' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '仓库状态', type: 'select' }, { prop: 'remark', label: '仓库说明', type: 'textarea' }]
</script>

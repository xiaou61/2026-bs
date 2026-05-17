<template><DataPage title="库存调拨" description="调拨编号、调拨仓点、调拨类型、经办人员、调拨时间和调拨状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'OPERATOR', 'INSPECTOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addInventoryTransfer, deleteInventoryTransfer, finishInventoryTransfer, getInventoryTransferPage, processInventoryTransfer, updateInventoryTransfer } from '../api'
const api = { page: getInventoryTransferPage, add: addInventoryTransfer, update: updateInventoryTransfer, delete: deleteInventoryTransfer, process: processInventoryTransfer, finish: finishInventoryTransfer }
const columns = [{ prop: 'recordNo', label: '调拨编号' }, { prop: 'recordName', label: '调拨仓点' }, { prop: 'category', label: '调拨类型' }, { prop: 'ownerName', label: '经办人员' }, { prop: 'planTime', label: '调拨时间' }, { prop: 'status', label: '调拨状态' }, { prop: 'remark', label: '调拨说明' }]
const formFields = [{ prop: 'recordNo', label: '调拨编号' }, { prop: 'recordName', label: '调拨仓点' }, { prop: 'category', label: '调拨类型' }, { prop: 'ownerName', label: '经办人员' }, { prop: 'planTime', label: '调拨时间' }, { prop: 'status', label: '调拨状态', type: 'select' }, { prop: 'remark', label: '调拨说明', type: 'textarea' }]
</script>

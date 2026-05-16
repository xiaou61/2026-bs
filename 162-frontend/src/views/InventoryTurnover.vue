<template><DataPage title="库存周转" description="周转编号、周转商品、周转类型、负责人、统计时间和周转状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'MANAGER', 'STOCK']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addInventoryTurnover, deleteInventoryTurnover, finishInventoryTurnover, getInventoryTurnoverPage, processInventoryTurnover, updateInventoryTurnover } from '../api'
const api = { page: getInventoryTurnoverPage, add: addInventoryTurnover, update: updateInventoryTurnover, delete: deleteInventoryTurnover, process: processInventoryTurnover, finish: finishInventoryTurnover }
const columns = [{ prop: 'recordNo', label: '周转编号' }, { prop: 'recordName', label: '周转商品' }, { prop: 'category', label: '周转类型' }, { prop: 'ownerName', label: '负责人' }, { prop: 'planTime', label: '统计时间' }, { prop: 'status', label: '周转状态' }, { prop: 'remark', label: '周转说明' }]
const formFields = [{ prop: 'recordNo', label: '周转编号' }, { prop: 'recordName', label: '周转商品' }, { prop: 'category', label: '周转类型' }, { prop: 'ownerName', label: '负责人' }, { prop: 'planTime', label: '统计时间' }, { prop: 'status', label: '周转状态', type: 'select' }, { prop: 'remark', label: '周转说明', type: 'textarea' }]
</script>

<template><DataPage title="寄卖物品" description="寄卖编号、物品名称、物品类型、寄卖人、上架时间和寄卖状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'OPERATOR', 'SELLER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addConsignmentItem, deleteConsignmentItem, finishConsignmentItem, getConsignmentItemPage, processConsignmentItem, updateConsignmentItem } from '../api'
const api = { page: getConsignmentItemPage, add: addConsignmentItem, update: updateConsignmentItem, delete: deleteConsignmentItem, process: processConsignmentItem, finish: finishConsignmentItem }
const columns = [{ prop: 'recordNo', label: '寄卖编号' }, { prop: 'recordName', label: '物品名称' }, { prop: 'category', label: '物品类型' }, { prop: 'ownerName', label: '寄卖人' }, { prop: 'planTime', label: '上架时间' }, { prop: 'status', label: '寄卖状态' }, { prop: 'remark', label: '物品描述' }]
const formFields = [{ prop: 'recordNo', label: '寄卖编号' }, { prop: 'recordName', label: '物品名称' }, { prop: 'category', label: '物品类型' }, { prop: 'ownerName', label: '寄卖人' }, { prop: 'planTime', label: '上架时间' }, { prop: 'status', label: '寄卖状态', type: 'select' }, { prop: 'remark', label: '物品描述', type: 'textarea' }]
</script>

<template><DataPage title="担保订单" description="订单编号、寄卖物品、交易类型、买家、下单时间和订单状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'OPERATOR', 'SELLER', 'BUYER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addEscrowOrder, deleteEscrowOrder, finishEscrowOrder, getEscrowOrderPage, processEscrowOrder, updateEscrowOrder } from '../api'
const api = { page: getEscrowOrderPage, add: addEscrowOrder, update: updateEscrowOrder, delete: deleteEscrowOrder, process: processEscrowOrder, finish: finishEscrowOrder }
const columns = [{ prop: 'recordNo', label: '订单编号' }, { prop: 'recordName', label: '寄卖物品' }, { prop: 'category', label: '交易类型' }, { prop: 'ownerName', label: '买家' }, { prop: 'planTime', label: '下单时间' }, { prop: 'status', label: '订单状态' }, { prop: 'remark', label: '订单说明' }]
const formFields = [{ prop: 'recordNo', label: '订单编号' }, { prop: 'recordName', label: '寄卖物品' }, { prop: 'category', label: '交易类型' }, { prop: 'ownerName', label: '买家' }, { prop: 'planTime', label: '下单时间' }, { prop: 'status', label: '订单状态', type: 'select' }, { prop: 'remark', label: '订单说明', type: 'textarea' }]
</script>

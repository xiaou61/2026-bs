<template><DataPage title="积分兑换" description="兑换编号、兑换居民、兑换类型、兑换人员、兑换时间和兑换状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'COMMUNITY', 'EXCHANGE', 'RESIDENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addExchangeOrder, deleteExchangeOrder, finishExchangeOrder, getExchangeOrderPage, processExchangeOrder, updateExchangeOrder } from '../api'
const api = { page: getExchangeOrderPage, add: addExchangeOrder, update: updateExchangeOrder, delete: deleteExchangeOrder, process: processExchangeOrder, finish: finishExchangeOrder }
const columns = [{ prop: 'recordNo', label: '兑换编号' }, { prop: 'recordName', label: '兑换居民' }, { prop: 'category', label: '兑换类型' }, { prop: 'ownerName', label: '兑换人员' }, { prop: 'planTime', label: '兑换时间' }, { prop: 'status', label: '兑换状态' }, { prop: 'remark', label: '兑换说明' }]
const formFields = [{ prop: 'recordNo', label: '兑换编号' }, { prop: 'recordName', label: '兑换居民' }, { prop: 'category', label: '兑换类型' }, { prop: 'ownerName', label: '兑换人员' }, { prop: 'planTime', label: '兑换时间' }, { prop: 'status', label: '兑换状态', type: 'select' }, { prop: 'remark', label: '兑换说明', type: 'textarea' }]
</script>

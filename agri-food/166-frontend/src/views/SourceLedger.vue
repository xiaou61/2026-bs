<template><DataPage title="进货台账" description="台账编号、进货商品、来源类型、供货单位、进货时间和台账状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'MARKET', 'VENDOR', 'INSPECTOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addSourceLedger, deleteSourceLedger, finishSourceLedger, getSourceLedgerPage, processSourceLedger, updateSourceLedger } from '../api'
const api = { page: getSourceLedgerPage, add: addSourceLedger, update: updateSourceLedger, delete: deleteSourceLedger, process: processSourceLedger, finish: finishSourceLedger }
const columns = [{ prop: 'recordNo', label: '台账编号' }, { prop: 'recordName', label: '进货商品' }, { prop: 'category', label: '来源类型' }, { prop: 'ownerName', label: '供货单位' }, { prop: 'planTime', label: '进货时间' }, { prop: 'status', label: '台账状态' }, { prop: 'remark', label: '台账说明' }]
const formFields = [{ prop: 'recordNo', label: '台账编号' }, { prop: 'recordName', label: '进货商品' }, { prop: 'category', label: '来源类型' }, { prop: 'ownerName', label: '供货单位' }, { prop: 'planTime', label: '进货时间' }, { prop: 'status', label: '台账状态', type: 'select' }, { prop: 'remark', label: '台账说明', type: 'textarea' }]
</script>

<template><DataPage title="展销结算" description="结算编号、结算周期、结算类型、财务人员、结算时间和结算状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'WORKSHOP', 'SALES']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addSalesSettlement, deleteSalesSettlement, finishSalesSettlement, getSalesSettlementPage, processSalesSettlement, updateSalesSettlement } from '../api'
const api = { page: getSalesSettlementPage, add: addSalesSettlement, update: updateSalesSettlement, delete: deleteSalesSettlement, process: processSalesSettlement, finish: finishSalesSettlement }
const columns = [{ prop: 'recordNo', label: '结算编号' }, { prop: 'recordName', label: '结算周期' }, { prop: 'category', label: '结算类型' }, { prop: 'ownerName', label: '财务人员' }, { prop: 'planTime', label: '结算时间' }, { prop: 'status', label: '结算状态' }, { prop: 'remark', label: '结算说明' }]
const formFields = [{ prop: 'recordNo', label: '结算编号' }, { prop: 'recordName', label: '结算周期' }, { prop: 'category', label: '结算类型' }, { prop: 'ownerName', label: '财务人员' }, { prop: 'planTime', label: '结算时间' }, { prop: 'status', label: '结算状态', type: 'select' }, { prop: 'remark', label: '结算说明', type: 'textarea' }]
</script>

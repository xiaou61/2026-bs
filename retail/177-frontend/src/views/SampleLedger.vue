<template><DataPage title="样品台账" description="样品编号、样品名称、样品类型、仓储人员、入库时间和样品状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'BASE', 'SELECTOR', 'SAMPLE', 'MERCHANT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addSampleLedger, deleteSampleLedger, finishSampleLedger, getSampleLedgerPage, processSampleLedger, updateSampleLedger } from '../api'
const api = { page: getSampleLedgerPage, add: addSampleLedger, update: updateSampleLedger, delete: deleteSampleLedger, process: processSampleLedger, finish: finishSampleLedger }
const columns = [{ prop: 'recordNo', label: '样品编号' }, { prop: 'recordName', label: '样品名称' }, { prop: 'category', label: '样品类型' }, { prop: 'ownerName', label: '仓储人员' }, { prop: 'planTime', label: '入库时间' }, { prop: 'status', label: '样品状态' }, { prop: 'remark', label: '样品说明' }]
const formFields = [{ prop: 'recordNo', label: '样品编号' }, { prop: 'recordName', label: '样品名称' }, { prop: 'category', label: '样品类型' }, { prop: 'ownerName', label: '仓储人员' }, { prop: 'planTime', label: '入库时间' }, { prop: 'status', label: '样品状态', type: 'select' }, { prop: 'remark', label: '样品说明', type: 'textarea' }]
</script>

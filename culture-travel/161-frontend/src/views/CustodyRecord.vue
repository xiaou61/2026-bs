<template><DataPage title="暂存保管" description="保管编号、保管物品、保管类型、保管人、入库时间和保管状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'SERVICE', 'SECURITY']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addCustodyRecord, deleteCustodyRecord, finishCustodyRecord, getCustodyRecordPage, processCustodyRecord, updateCustodyRecord } from '../api'
const api = { page: getCustodyRecordPage, add: addCustodyRecord, update: updateCustodyRecord, delete: deleteCustodyRecord, process: processCustodyRecord, finish: finishCustodyRecord }
const columns = [{ prop: 'recordNo', label: '保管编号' }, { prop: 'recordName', label: '保管物品' }, { prop: 'category', label: '保管类型' }, { prop: 'ownerName', label: '保管人' }, { prop: 'planTime', label: '入库时间' }, { prop: 'status', label: '保管状态' }, { prop: 'remark', label: '保管说明' }]
const formFields = [{ prop: 'recordNo', label: '保管编号' }, { prop: 'recordName', label: '保管物品' }, { prop: 'category', label: '保管类型' }, { prop: 'ownerName', label: '保管人' }, { prop: 'planTime', label: '入库时间' }, { prop: 'status', label: '保管状态', type: 'select' }, { prop: 'remark', label: '保管说明', type: 'textarea' }]
</script>

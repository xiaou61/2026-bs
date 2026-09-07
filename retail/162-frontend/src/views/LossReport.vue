<template><DataPage title="报损记录" description="报损编号、报损商品、报损原因、登记人、报损时间和报损状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'MANAGER', 'CLERK', 'STOCK']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addLossReport, deleteLossReport, finishLossReport, getLossReportPage, processLossReport, updateLossReport } from '../api'
const api = { page: getLossReportPage, add: addLossReport, update: updateLossReport, delete: deleteLossReport, process: processLossReport, finish: finishLossReport }
const columns = [{ prop: 'recordNo', label: '报损编号' }, { prop: 'recordName', label: '报损商品' }, { prop: 'category', label: '报损原因' }, { prop: 'ownerName', label: '登记人' }, { prop: 'planTime', label: '报损时间' }, { prop: 'status', label: '报损状态' }, { prop: 'remark', label: '报损说明' }]
const formFields = [{ prop: 'recordNo', label: '报损编号' }, { prop: 'recordName', label: '报损商品' }, { prop: 'category', label: '报损原因' }, { prop: 'ownerName', label: '登记人' }, { prop: 'planTime', label: '报损时间' }, { prop: 'status', label: '报损状态', type: 'select' }, { prop: 'remark', label: '报损说明', type: 'textarea' }]
</script>

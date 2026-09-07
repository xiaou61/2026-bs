<template><DataPage title="风险复核" description="复核编号、处方记录、风险类型、复核人员、复核时间和复核状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'PHARMACY', 'PHARMACIST']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addRiskCheck, deleteRiskCheck, finishRiskCheck, getRiskCheckPage, processRiskCheck, updateRiskCheck } from '../api'
const api = { page: getRiskCheckPage, add: addRiskCheck, update: updateRiskCheck, delete: deleteRiskCheck, process: processRiskCheck, finish: finishRiskCheck }
const columns = [{ prop: 'recordNo', label: '复核编号' }, { prop: 'recordName', label: '处方记录' }, { prop: 'category', label: '风险类型' }, { prop: 'ownerName', label: '复核人员' }, { prop: 'planTime', label: '复核时间' }, { prop: 'status', label: '复核状态' }, { prop: 'remark', label: '复核说明' }]
const formFields = [{ prop: 'recordNo', label: '复核编号' }, { prop: 'recordName', label: '处方记录' }, { prop: 'category', label: '风险类型' }, { prop: 'ownerName', label: '复核人员' }, { prop: 'planTime', label: '复核时间' }, { prop: 'status', label: '复核状态', type: 'select' }, { prop: 'remark', label: '复核说明', type: 'textarea' }]
</script>

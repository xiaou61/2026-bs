<template><DataPage title="风险预警" description="预警编号、预警对象、预警类型、处置负责人、预警时间和预警状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'MARKET', 'INSPECTOR', 'SAMPLER', 'REGULATOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addRiskAlert, deleteRiskAlert, finishRiskAlert, getRiskAlertPage, processRiskAlert, updateRiskAlert } from '../api'
const api = { page: getRiskAlertPage, add: addRiskAlert, update: updateRiskAlert, delete: deleteRiskAlert, process: processRiskAlert, finish: finishRiskAlert }
const columns = [{ prop: 'recordNo', label: '预警编号' }, { prop: 'recordName', label: '预警对象' }, { prop: 'category', label: '预警类型' }, { prop: 'ownerName', label: '处置负责人' }, { prop: 'planTime', label: '预警时间' }, { prop: 'status', label: '预警状态' }, { prop: 'remark', label: '预警说明' }]
const formFields = [{ prop: 'recordNo', label: '预警编号' }, { prop: 'recordName', label: '预警对象' }, { prop: 'category', label: '预警类型' }, { prop: 'ownerName', label: '处置负责人' }, { prop: 'planTime', label: '预警时间' }, { prop: 'status', label: '预警状态', type: 'select' }, { prop: 'remark', label: '预警说明', type: 'textarea' }]
</script>

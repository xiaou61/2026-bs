<template><DataPage title="风险分层" description="分层编号、评估患者、风险类型、评估医生、评估时间和风险状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CENTER', 'DOCTOR', 'NURSE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addRiskStratification, deleteRiskStratification, finishRiskStratification, getRiskStratificationPage, processRiskStratification, updateRiskStratification } from '../api'
const api = { page: getRiskStratificationPage, add: addRiskStratification, update: updateRiskStratification, delete: deleteRiskStratification, process: processRiskStratification, finish: finishRiskStratification }
const columns = [{ prop: 'recordNo', label: '分层编号' }, { prop: 'recordName', label: '评估患者' }, { prop: 'category', label: '风险类型' }, { prop: 'ownerName', label: '评估医生' }, { prop: 'planTime', label: '评估时间' }, { prop: 'status', label: '风险状态' }, { prop: 'remark', label: '风险说明' }]
const formFields = [{ prop: 'recordNo', label: '分层编号' }, { prop: 'recordName', label: '评估患者' }, { prop: 'category', label: '风险类型' }, { prop: 'ownerName', label: '评估医生' }, { prop: 'planTime', label: '评估时间' }, { prop: 'status', label: '风险状态', type: 'select' }, { prop: 'remark', label: '风险说明', type: 'textarea' }]
</script>

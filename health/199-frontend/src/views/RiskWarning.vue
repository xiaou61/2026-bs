<template><DataPage title="风险提醒" description="提醒编号、会员姓名、风险类型、评估人员、提醒时间和处理状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CENTER', 'ASSESSOR', 'THERAPIST', 'COACH']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addRiskWarning, deleteRiskWarning, finishRiskWarning, getRiskWarningPage, processRiskWarning, updateRiskWarning } from '../api'
const api = { page: getRiskWarningPage, add: addRiskWarning, update: updateRiskWarning, delete: deleteRiskWarning, process: processRiskWarning, finish: finishRiskWarning }
const columns = [{ prop: 'recordNo', label: '提醒编号' }, { prop: 'recordName', label: '会员姓名' }, { prop: 'category', label: '风险类型' }, { prop: 'ownerName', label: '评估人员' }, { prop: 'planTime', label: '提醒时间' }, { prop: 'status', label: '处理状态' }, { prop: 'remark', label: '提醒说明' }]
const formFields = [{ prop: 'recordNo', label: '提醒编号' }, { prop: 'recordName', label: '会员姓名' }, { prop: 'category', label: '风险类型' }, { prop: 'ownerName', label: '评估人员' }, { prop: 'planTime', label: '提醒时间' }, { prop: 'status', label: '处理状态', type: 'select' }, { prop: 'remark', label: '提醒说明', type: 'textarea' }]
</script>

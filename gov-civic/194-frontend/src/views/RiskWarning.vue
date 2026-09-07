<template><DataPage title="风险预警" description="预警编号、危废批次、预警类型、处理人员、预警时间和预警状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'PARK', 'WAREHOUSE', 'INSPECTOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addRiskWarning, deleteRiskWarning, finishRiskWarning, getRiskWarningPage, processRiskWarning, updateRiskWarning } from '../api'
const api = { page: getRiskWarningPage, add: addRiskWarning, update: updateRiskWarning, delete: deleteRiskWarning, process: processRiskWarning, finish: finishRiskWarning }
const columns = [{ prop: 'recordNo', label: '预警编号' }, { prop: 'recordName', label: '危废批次' }, { prop: 'category', label: '预警类型' }, { prop: 'ownerName', label: '处理人员' }, { prop: 'planTime', label: '预警时间' }, { prop: 'status', label: '预警状态' }, { prop: 'remark', label: '预警说明' }]
const formFields = [{ prop: 'recordNo', label: '预警编号' }, { prop: 'recordName', label: '危废批次' }, { prop: 'category', label: '预警类型' }, { prop: 'ownerName', label: '处理人员' }, { prop: 'planTime', label: '预警时间' }, { prop: 'status', label: '预警状态', type: 'select' }, { prop: 'remark', label: '预警说明', type: 'textarea' }]
</script>

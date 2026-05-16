<template><DataPage title="安全告警" description="告警编号、告警位置、告警类型、处理人员、告警时间和告警状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CENTER', 'SECURITY', 'TEACHER', 'PARENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addSafetyAlert, deleteSafetyAlert, finishSafetyAlert, getSafetyAlertPage, processSafetyAlert, updateSafetyAlert } from '../api'
const api = { page: getSafetyAlertPage, add: addSafetyAlert, update: updateSafetyAlert, delete: deleteSafetyAlert, process: processSafetyAlert, finish: finishSafetyAlert }
const columns = [{ prop: 'recordNo', label: '告警编号' }, { prop: 'recordName', label: '告警位置' }, { prop: 'category', label: '告警类型' }, { prop: 'ownerName', label: '处理人员' }, { prop: 'planTime', label: '告警时间' }, { prop: 'status', label: '告警状态' }, { prop: 'remark', label: '告警说明' }]
const formFields = [{ prop: 'recordNo', label: '告警编号' }, { prop: 'recordName', label: '告警位置' }, { prop: 'category', label: '告警类型' }, { prop: 'ownerName', label: '处理人员' }, { prop: 'planTime', label: '告警时间' }, { prop: 'status', label: '告警状态', type: 'select' }, { prop: 'remark', label: '告警说明', type: 'textarea' }]
</script>

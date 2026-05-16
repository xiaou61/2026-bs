<template><DataPage title="灭菌记录" description="记录编号、灭菌批次、灭菌类型、灭菌人员、灭菌时间和记录状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CSSD', 'STERILE', 'QA']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addSterilizationRecord, deleteSterilizationRecord, finishSterilizationRecord, getSterilizationRecordPage, processSterilizationRecord, updateSterilizationRecord } from '../api'
const api = { page: getSterilizationRecordPage, add: addSterilizationRecord, update: updateSterilizationRecord, delete: deleteSterilizationRecord, process: processSterilizationRecord, finish: finishSterilizationRecord }
const columns = [{ prop: 'recordNo', label: '记录编号' }, { prop: 'recordName', label: '灭菌批次' }, { prop: 'category', label: '灭菌类型' }, { prop: 'ownerName', label: '灭菌人员' }, { prop: 'planTime', label: '灭菌时间' }, { prop: 'status', label: '记录状态' }, { prop: 'remark', label: '记录说明' }]
const formFields = [{ prop: 'recordNo', label: '记录编号' }, { prop: 'recordName', label: '灭菌批次' }, { prop: 'category', label: '灭菌类型' }, { prop: 'ownerName', label: '灭菌人员' }, { prop: 'planTime', label: '灭菌时间' }, { prop: 'status', label: '记录状态', type: 'select' }, { prop: 'remark', label: '记录说明', type: 'textarea' }]
</script>

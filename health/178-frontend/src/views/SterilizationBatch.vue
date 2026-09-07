<template><DataPage title="灭菌批次" description="批次编号、灭菌锅次、灭菌类型、灭菌人员、开始时间和批次状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CSSD', 'STERILE', 'QA']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addSterilizationBatch, deleteSterilizationBatch, finishSterilizationBatch, getSterilizationBatchPage, processSterilizationBatch, updateSterilizationBatch } from '../api'
const api = { page: getSterilizationBatchPage, add: addSterilizationBatch, update: updateSterilizationBatch, delete: deleteSterilizationBatch, process: processSterilizationBatch, finish: finishSterilizationBatch }
const columns = [{ prop: 'recordNo', label: '批次编号' }, { prop: 'recordName', label: '灭菌锅次' }, { prop: 'category', label: '灭菌类型' }, { prop: 'ownerName', label: '灭菌人员' }, { prop: 'planTime', label: '开始时间' }, { prop: 'status', label: '批次状态' }, { prop: 'remark', label: '批次说明' }]
const formFields = [{ prop: 'recordNo', label: '批次编号' }, { prop: 'recordName', label: '灭菌锅次' }, { prop: 'category', label: '灭菌类型' }, { prop: 'ownerName', label: '灭菌人员' }, { prop: 'planTime', label: '开始时间' }, { prop: 'status', label: '批次状态', type: 'select' }, { prop: 'remark', label: '批次说明', type: 'textarea' }]
</script>

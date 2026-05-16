<template><DataPage title="缺损上报" description="上报编号、缺损器械、缺损类型、上报人员、上报时间和缺损状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'ORNURSE', 'CSSD', 'STERILE', 'QA']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addDefectReport, deleteDefectReport, finishDefectReport, getDefectReportPage, processDefectReport, updateDefectReport } from '../api'
const api = { page: getDefectReportPage, add: addDefectReport, update: updateDefectReport, delete: deleteDefectReport, process: processDefectReport, finish: finishDefectReport }
const columns = [{ prop: 'recordNo', label: '上报编号' }, { prop: 'recordName', label: '缺损器械' }, { prop: 'category', label: '缺损类型' }, { prop: 'ownerName', label: '上报人员' }, { prop: 'planTime', label: '上报时间' }, { prop: 'status', label: '缺损状态' }, { prop: 'remark', label: '上报说明' }]
const formFields = [{ prop: 'recordNo', label: '上报编号' }, { prop: 'recordName', label: '缺损器械' }, { prop: 'category', label: '缺损类型' }, { prop: 'ownerName', label: '上报人员' }, { prop: 'planTime', label: '上报时间' }, { prop: 'status', label: '缺损状态', type: 'select' }, { prop: 'remark', label: '上报说明', type: 'textarea' }]
</script>

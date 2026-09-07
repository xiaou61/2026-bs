<template><DataPage title="检测结果" description="检测编号、检测样品、检测项目、检测机构、出具时间和检测状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'MARKET', 'SAMPLER', 'REGULATOR', 'VENDOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addTestResult, deleteTestResult, finishTestResult, getTestResultPage, processTestResult, updateTestResult } from '../api'
const api = { page: getTestResultPage, add: addTestResult, update: updateTestResult, delete: deleteTestResult, process: processTestResult, finish: finishTestResult }
const columns = [{ prop: 'recordNo', label: '检测编号' }, { prop: 'recordName', label: '检测样品' }, { prop: 'category', label: '检测项目' }, { prop: 'ownerName', label: '检测机构' }, { prop: 'planTime', label: '出具时间' }, { prop: 'status', label: '检测状态' }, { prop: 'remark', label: '检测说明' }]
const formFields = [{ prop: 'recordNo', label: '检测编号' }, { prop: 'recordName', label: '检测样品' }, { prop: 'category', label: '检测项目' }, { prop: 'ownerName', label: '检测机构' }, { prop: 'planTime', label: '出具时间' }, { prop: 'status', label: '检测状态', type: 'select' }, { prop: 'remark', label: '检测说明', type: 'textarea' }]
</script>

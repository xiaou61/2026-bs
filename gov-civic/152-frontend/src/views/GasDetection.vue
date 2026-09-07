<template><DataPage title="气体检测" description="检测编号、作业票号、检测类型、检测人、检测时间和检测状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'SAFETY', 'GUARDIAN']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addGasDetection, deleteGasDetection, finishGasDetection, getGasDetectionPage, processGasDetection, updateGasDetection } from '../api'
const api = { page: getGasDetectionPage, add: addGasDetection, update: updateGasDetection, delete: deleteGasDetection, process: processGasDetection, finish: finishGasDetection }
const columns = [{ prop: 'recordNo', label: '检测编号' }, { prop: 'recordName', label: '作业票号' }, { prop: 'category', label: '检测类型' }, { prop: 'ownerName', label: '检测人' }, { prop: 'planTime', label: '检测时间' }, { prop: 'status', label: '检测状态' }, { prop: 'remark', label: '检测结果' }]
const formFields = [{ prop: 'recordNo', label: '检测编号' }, { prop: 'recordName', label: '作业票号' }, { prop: 'category', label: '检测类型' }, { prop: 'ownerName', label: '检测人' }, { prop: 'planTime', label: '检测时间' }, { prop: 'status', label: '检测状态', type: 'select' }, { prop: 'remark', label: '检测结果', type: 'textarea' }]
</script>

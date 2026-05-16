<template><DataPage title="课消记录" description="课消编号、课消课程、课消类型、确认人、课消时间和课消状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'ACADEMIC', 'TEACHER', 'FINANCE', 'PARENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addConsumptionRecord, deleteConsumptionRecord, finishConsumptionRecord, getConsumptionRecordPage, processConsumptionRecord, updateConsumptionRecord } from '../api'
const api = { page: getConsumptionRecordPage, add: addConsumptionRecord, update: updateConsumptionRecord, delete: deleteConsumptionRecord, process: processConsumptionRecord, finish: finishConsumptionRecord }
const columns = [{ prop: 'recordNo', label: '课消编号' }, { prop: 'recordName', label: '课消课程' }, { prop: 'category', label: '课消类型' }, { prop: 'ownerName', label: '确认人' }, { prop: 'planTime', label: '课消时间' }, { prop: 'status', label: '课消状态' }, { prop: 'remark', label: '课消说明' }]
const formFields = [{ prop: 'recordNo', label: '课消编号' }, { prop: 'recordName', label: '课消课程' }, { prop: 'category', label: '课消类型' }, { prop: 'ownerName', label: '确认人' }, { prop: 'planTime', label: '课消时间' }, { prop: 'status', label: '课消状态', type: 'select' }, { prop: 'remark', label: '课消说明', type: 'textarea' }]
</script>

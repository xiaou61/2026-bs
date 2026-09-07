<template><DataPage title="巡线记录" description="记录编号、巡线区段、巡线类型、巡线人员、巡线时间和记录状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'DISPATCH', 'PATROL', 'SUPERVISOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addPatrolRecord, deletePatrolRecord, finishPatrolRecord, getPatrolRecordPage, processPatrolRecord, updatePatrolRecord } from '../api'
const api = { page: getPatrolRecordPage, add: addPatrolRecord, update: updatePatrolRecord, delete: deletePatrolRecord, process: processPatrolRecord, finish: finishPatrolRecord }
const columns = [{ prop: 'recordNo', label: '记录编号' }, { prop: 'recordName', label: '巡线区段' }, { prop: 'category', label: '巡线类型' }, { prop: 'ownerName', label: '巡线人员' }, { prop: 'planTime', label: '巡线时间' }, { prop: 'status', label: '记录状态' }, { prop: 'remark', label: '记录说明' }]
const formFields = [{ prop: 'recordNo', label: '记录编号' }, { prop: 'recordName', label: '巡线区段' }, { prop: 'category', label: '巡线类型' }, { prop: 'ownerName', label: '巡线人员' }, { prop: 'planTime', label: '巡线时间' }, { prop: 'status', label: '记录状态', type: 'select' }, { prop: 'remark', label: '记录说明', type: 'textarea' }]
</script>

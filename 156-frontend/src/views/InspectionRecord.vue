<template><DataPage title="巡查记录" description="巡查编号、巡查区域、巡查类型、巡查人、巡查时间和巡查状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'LOGISTICS', 'COUNSELOR', 'ENERGY']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addInspectionRecord, deleteInspectionRecord, finishInspectionRecord, getInspectionRecordPage, processInspectionRecord, updateInspectionRecord } from '../api'
const api = { page: getInspectionRecordPage, add: addInspectionRecord, update: updateInspectionRecord, delete: deleteInspectionRecord, process: processInspectionRecord, finish: finishInspectionRecord }
const columns = [{ prop: 'recordNo', label: '巡查编号' }, { prop: 'recordName', label: '巡查区域' }, { prop: 'category', label: '巡查类型' }, { prop: 'ownerName', label: '巡查人' }, { prop: 'planTime', label: '巡查时间' }, { prop: 'status', label: '巡查状态' }, { prop: 'remark', label: '巡查说明' }]
const formFields = [{ prop: 'recordNo', label: '巡查编号' }, { prop: 'recordName', label: '巡查区域' }, { prop: 'category', label: '巡查类型' }, { prop: 'ownerName', label: '巡查人' }, { prop: 'planTime', label: '巡查时间' }, { prop: 'status', label: '巡查状态', type: 'select' }, { prop: 'remark', label: '巡查说明', type: 'textarea' }]
</script>

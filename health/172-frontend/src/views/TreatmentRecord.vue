<template><DataPage title="治疗记录" description="记录编号、治疗患者、治疗类型、治疗医生、治疗时间和治疗状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'DENTIST', 'NURSE', 'PATIENT', 'CASHIER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addTreatmentRecord, deleteTreatmentRecord, finishTreatmentRecord, getTreatmentRecordPage, processTreatmentRecord, updateTreatmentRecord } from '../api'
const api = { page: getTreatmentRecordPage, add: addTreatmentRecord, update: updateTreatmentRecord, delete: deleteTreatmentRecord, process: processTreatmentRecord, finish: finishTreatmentRecord }
const columns = [{ prop: 'recordNo', label: '记录编号' }, { prop: 'recordName', label: '治疗患者' }, { prop: 'category', label: '治疗类型' }, { prop: 'ownerName', label: '治疗医生' }, { prop: 'planTime', label: '治疗时间' }, { prop: 'status', label: '治疗状态' }, { prop: 'remark', label: '治疗说明' }]
const formFields = [{ prop: 'recordNo', label: '记录编号' }, { prop: 'recordName', label: '治疗患者' }, { prop: 'category', label: '治疗类型' }, { prop: 'ownerName', label: '治疗医生' }, { prop: 'planTime', label: '治疗时间' }, { prop: 'status', label: '治疗状态', type: 'select' }, { prop: 'remark', label: '治疗说明', type: 'textarea' }]
</script>

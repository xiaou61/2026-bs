<template><DataPage title="处方登记" description="处方编号、顾客姓名、处方类型、登记人员、登记时间和处方状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'PHARMACY', 'PHARMACIST', 'CLERK', 'CUSTOMER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addPrescriptionRecord, deletePrescriptionRecord, finishPrescriptionRecord, getPrescriptionRecordPage, processPrescriptionRecord, updatePrescriptionRecord } from '../api'
const api = { page: getPrescriptionRecordPage, add: addPrescriptionRecord, update: updatePrescriptionRecord, delete: deletePrescriptionRecord, process: processPrescriptionRecord, finish: finishPrescriptionRecord }
const columns = [{ prop: 'recordNo', label: '处方编号' }, { prop: 'recordName', label: '顾客姓名' }, { prop: 'category', label: '处方类型' }, { prop: 'ownerName', label: '登记人员' }, { prop: 'planTime', label: '登记时间' }, { prop: 'status', label: '处方状态' }, { prop: 'remark', label: '处方说明' }]
const formFields = [{ prop: 'recordNo', label: '处方编号' }, { prop: 'recordName', label: '顾客姓名' }, { prop: 'category', label: '处方类型' }, { prop: 'ownerName', label: '登记人员' }, { prop: 'planTime', label: '登记时间' }, { prop: 'status', label: '处方状态', type: 'select' }, { prop: 'remark', label: '处方说明', type: 'textarea' }]
</script>

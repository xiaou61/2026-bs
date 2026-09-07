<template><DataPage title="兽医处置" description="处置编号、异常告警、处置类型、兽医人员、处置时间和处置状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'LAB', 'BREEDER', 'VET']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addVeterinaryTreatment, deleteVeterinaryTreatment, finishVeterinaryTreatment, getVeterinaryTreatmentPage, processVeterinaryTreatment, updateVeterinaryTreatment } from '../api'
const api = { page: getVeterinaryTreatmentPage, add: addVeterinaryTreatment, update: updateVeterinaryTreatment, delete: deleteVeterinaryTreatment, process: processVeterinaryTreatment, finish: finishVeterinaryTreatment }
const columns = [{ prop: 'recordNo', label: '处置编号' }, { prop: 'recordName', label: '异常告警' }, { prop: 'category', label: '处置类型' }, { prop: 'ownerName', label: '兽医人员' }, { prop: 'planTime', label: '处置时间' }, { prop: 'status', label: '处置状态' }, { prop: 'remark', label: '处置说明' }]
const formFields = [{ prop: 'recordNo', label: '处置编号' }, { prop: 'recordName', label: '异常告警' }, { prop: 'category', label: '处置类型' }, { prop: 'ownerName', label: '兽医人员' }, { prop: 'planTime', label: '处置时间' }, { prop: 'status', label: '处置状态', type: 'select' }, { prop: 'remark', label: '处置说明', type: 'textarea' }]
</script>

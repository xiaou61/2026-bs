<template><DataPage title="服药打卡" description="打卡编号、用药患者、打卡类型、核验人员、打卡时间和依从状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CENTER', 'NURSE', 'PHARMACIST', 'RESIDENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addMedicationAdherence, deleteMedicationAdherence, finishMedicationAdherence, getMedicationAdherencePage, processMedicationAdherence, updateMedicationAdherence } from '../api'
const api = { page: getMedicationAdherencePage, add: addMedicationAdherence, update: updateMedicationAdherence, delete: deleteMedicationAdherence, process: processMedicationAdherence, finish: finishMedicationAdherence }
const columns = [{ prop: 'recordNo', label: '打卡编号' }, { prop: 'recordName', label: '用药患者' }, { prop: 'category', label: '打卡类型' }, { prop: 'ownerName', label: '核验人员' }, { prop: 'planTime', label: '打卡时间' }, { prop: 'status', label: '依从状态' }, { prop: 'remark', label: '打卡说明' }]
const formFields = [{ prop: 'recordNo', label: '打卡编号' }, { prop: 'recordName', label: '用药患者' }, { prop: 'category', label: '打卡类型' }, { prop: 'ownerName', label: '核验人员' }, { prop: 'planTime', label: '打卡时间' }, { prop: 'status', label: '依从状态', type: 'select' }, { prop: 'remark', label: '打卡说明', type: 'textarea' }]
</script>

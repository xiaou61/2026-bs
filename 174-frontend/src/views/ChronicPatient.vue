<template><DataPage title="患者档案" description="患者编号、患者姓名、慢病类型、签约医生、建档时间和档案状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CENTER', 'DOCTOR', 'NURSE', 'RESIDENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addChronicPatient, deleteChronicPatient, finishChronicPatient, getChronicPatientPage, processChronicPatient, updateChronicPatient } from '../api'
const api = { page: getChronicPatientPage, add: addChronicPatient, update: updateChronicPatient, delete: deleteChronicPatient, process: processChronicPatient, finish: finishChronicPatient }
const columns = [{ prop: 'recordNo', label: '患者编号' }, { prop: 'recordName', label: '患者姓名' }, { prop: 'category', label: '慢病类型' }, { prop: 'ownerName', label: '签约医生' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '档案状态' }, { prop: 'remark', label: '档案说明' }]
const formFields = [{ prop: 'recordNo', label: '患者编号' }, { prop: 'recordName', label: '患者姓名' }, { prop: 'category', label: '慢病类型' }, { prop: 'ownerName', label: '签约医生' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '档案状态', type: 'select' }, { prop: 'remark', label: '档案说明', type: 'textarea' }]
</script>

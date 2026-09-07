<template><DataPage title="患者档案" description="患者编号、患者姓名、口腔问题、接诊人员、建档时间和患者状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'RECEPTION', 'DENTIST', 'NURSE', 'PATIENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addPatientProfile, deletePatientProfile, finishPatientProfile, getPatientProfilePage, processPatientProfile, updatePatientProfile } from '../api'
const api = { page: getPatientProfilePage, add: addPatientProfile, update: updatePatientProfile, delete: deletePatientProfile, process: processPatientProfile, finish: finishPatientProfile }
const columns = [{ prop: 'recordNo', label: '患者编号' }, { prop: 'recordName', label: '患者姓名' }, { prop: 'category', label: '口腔问题' }, { prop: 'ownerName', label: '接诊人员' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '患者状态' }, { prop: 'remark', label: '患者说明' }]
const formFields = [{ prop: 'recordNo', label: '患者编号' }, { prop: 'recordName', label: '患者姓名' }, { prop: 'category', label: '口腔问题' }, { prop: 'ownerName', label: '接诊人员' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '患者状态', type: 'select' }, { prop: 'remark', label: '患者说明', type: 'textarea' }]
</script>

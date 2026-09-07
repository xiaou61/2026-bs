<template><DataPage title="患者档案" description="档案编号、患者姓名、护理等级、联系人、入院时间和陪护状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'HOSPITAL', 'COORDINATOR', 'FAMILY']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addPatientProfile, deletePatientProfile, finishPatientProfile, getPatientProfilePage, processPatientProfile, updatePatientProfile } from '../api'
const api = { page: getPatientProfilePage, add: addPatientProfile, update: updatePatientProfile, delete: deletePatientProfile, process: processPatientProfile, finish: finishPatientProfile }
const columns = [{ prop: 'recordNo', label: '档案编号' }, { prop: 'recordName', label: '患者姓名' }, { prop: 'category', label: '护理等级' }, { prop: 'ownerName', label: '联系人' }, { prop: 'planTime', label: '入院时间' }, { prop: 'status', label: '陪护状态' }, { prop: 'remark', label: '档案说明' }]
const formFields = [{ prop: 'recordNo', label: '档案编号' }, { prop: 'recordName', label: '患者姓名' }, { prop: 'category', label: '护理等级' }, { prop: 'ownerName', label: '联系人' }, { prop: 'planTime', label: '入院时间' }, { prop: 'status', label: '陪护状态', type: 'select' }, { prop: 'remark', label: '档案说明', type: 'textarea' }]
</script>

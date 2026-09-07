<template><DataPage title="用人单位" description="单位编号、单位名称、行业类型、联系人、建档时间和单位状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CAREER', 'EMPLOYER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addEmployerProfile, deleteEmployerProfile, finishEmployerProfile, getEmployerProfilePage, processEmployerProfile, updateEmployerProfile } from '../api'
const api = { page: getEmployerProfilePage, add: addEmployerProfile, update: updateEmployerProfile, delete: deleteEmployerProfile, process: processEmployerProfile, finish: finishEmployerProfile }
const columns = [{ prop: 'recordNo', label: '单位编号' }, { prop: 'recordName', label: '单位名称' }, { prop: 'category', label: '行业类型' }, { prop: 'ownerName', label: '联系人' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '单位状态' }, { prop: 'remark', label: '单位说明' }]
const formFields = [{ prop: 'recordNo', label: '单位编号' }, { prop: 'recordName', label: '单位名称' }, { prop: 'category', label: '行业类型' }, { prop: 'ownerName', label: '联系人' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '单位状态', type: 'select' }, { prop: 'remark', label: '单位说明', type: 'textarea' }]
</script>

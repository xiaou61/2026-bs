<template><DataPage title="诊室档案" description="诊室编号、诊室名称、诊室类型、负责人员、启用时间和诊室状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'RECEPTION', 'DENTIST', 'NURSE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addClinicRoom, deleteClinicRoom, finishClinicRoom, getClinicRoomPage, processClinicRoom, updateClinicRoom } from '../api'
const api = { page: getClinicRoomPage, add: addClinicRoom, update: updateClinicRoom, delete: deleteClinicRoom, process: processClinicRoom, finish: finishClinicRoom }
const columns = [{ prop: 'recordNo', label: '诊室编号' }, { prop: 'recordName', label: '诊室名称' }, { prop: 'category', label: '诊室类型' }, { prop: 'ownerName', label: '负责人员' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '诊室状态' }, { prop: 'remark', label: '诊室说明' }]
const formFields = [{ prop: 'recordNo', label: '诊室编号' }, { prop: 'recordName', label: '诊室名称' }, { prop: 'category', label: '诊室类型' }, { prop: 'ownerName', label: '负责人员' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '诊室状态', type: 'select' }, { prop: 'remark', label: '诊室说明', type: 'textarea' }]
</script>

<template><DataPage title="病区档案" description="病区编号、病区名称、科室类型、病区负责人、建档时间和病区状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'HOSPITAL']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addHospitalWard, deleteHospitalWard, finishHospitalWard, getHospitalWardPage, processHospitalWard, updateHospitalWard } from '../api'
const api = { page: getHospitalWardPage, add: addHospitalWard, update: updateHospitalWard, delete: deleteHospitalWard, process: processHospitalWard, finish: finishHospitalWard }
const columns = [{ prop: 'recordNo', label: '病区编号' }, { prop: 'recordName', label: '病区名称' }, { prop: 'category', label: '科室类型' }, { prop: 'ownerName', label: '病区负责人' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '病区状态' }, { prop: 'remark', label: '病区说明' }]
const formFields = [{ prop: 'recordNo', label: '病区编号' }, { prop: 'recordName', label: '病区名称' }, { prop: 'category', label: '科室类型' }, { prop: 'ownerName', label: '病区负责人' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '病区状态', type: 'select' }, { prop: 'remark', label: '病区说明', type: 'textarea' }]
</script>

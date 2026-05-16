<template><DataPage title="接诊排班" description="排班编号、坐诊医生、诊室区域、班次类型、排班时间和排班状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'RECEPTION', 'DOCTOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addClinicSchedule, deleteClinicSchedule, finishClinicSchedule, getClinicSchedulePage, processClinicSchedule, updateClinicSchedule } from '../api'
const api = { page: getClinicSchedulePage, add: addClinicSchedule, update: updateClinicSchedule, delete: deleteClinicSchedule, process: processClinicSchedule, finish: finishClinicSchedule }
const columns = [{ prop: 'recordNo', label: '排班编号' }, { prop: 'recordName', label: '坐诊医生' }, { prop: 'category', label: '诊室区域' }, { prop: 'ownerName', label: '班次类型' }, { prop: 'planTime', label: '排班时间' }, { prop: 'status', label: '排班状态' }, { prop: 'remark', label: '排班说明' }]
const formFields = [{ prop: 'recordNo', label: '排班编号' }, { prop: 'recordName', label: '坐诊医生' }, { prop: 'category', label: '诊室区域' }, { prop: 'ownerName', label: '班次类型' }, { prop: 'planTime', label: '排班时间' }, { prop: 'status', label: '排班状态', type: 'select' }, { prop: 'remark', label: '排班说明', type: 'textarea' }]
</script>

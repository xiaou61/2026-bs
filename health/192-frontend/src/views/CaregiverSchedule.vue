<template><DataPage title="护工排班" description="排班编号、护工姓名、排班班次、排班人员、排班时间和排班状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'HOSPITAL', 'COORDINATOR', 'CAREGIVER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addCaregiverSchedule, deleteCaregiverSchedule, finishCaregiverSchedule, getCaregiverSchedulePage, processCaregiverSchedule, updateCaregiverSchedule } from '../api'
const api = { page: getCaregiverSchedulePage, add: addCaregiverSchedule, update: updateCaregiverSchedule, delete: deleteCaregiverSchedule, process: processCaregiverSchedule, finish: finishCaregiverSchedule }
const columns = [{ prop: 'recordNo', label: '排班编号' }, { prop: 'recordName', label: '护工姓名' }, { prop: 'category', label: '排班班次' }, { prop: 'ownerName', label: '排班人员' }, { prop: 'planTime', label: '排班时间' }, { prop: 'status', label: '排班状态' }, { prop: 'remark', label: '排班说明' }]
const formFields = [{ prop: 'recordNo', label: '排班编号' }, { prop: 'recordName', label: '护工姓名' }, { prop: 'category', label: '排班班次' }, { prop: 'ownerName', label: '排班人员' }, { prop: 'planTime', label: '排班时间' }, { prop: 'status', label: '排班状态', type: 'select' }, { prop: 'remark', label: '排班说明', type: 'textarea' }]
</script>

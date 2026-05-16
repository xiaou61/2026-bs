<template><DataPage title="护理记录" description="记录编号、护理老人、护理类型、护理人员、护理时间和护理状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'NURSING', 'CAREGIVER', 'FAMILY', 'DIRECTOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addCareRecord, deleteCareRecord, finishCareRecord, getCareRecordPage, processCareRecord, updateCareRecord } from '../api'
const api = { page: getCareRecordPage, add: addCareRecord, update: updateCareRecord, delete: deleteCareRecord, process: processCareRecord, finish: finishCareRecord }
const columns = [{ prop: 'recordNo', label: '记录编号' }, { prop: 'recordName', label: '护理老人' }, { prop: 'category', label: '护理类型' }, { prop: 'ownerName', label: '护理人员' }, { prop: 'planTime', label: '护理时间' }, { prop: 'status', label: '护理状态' }, { prop: 'remark', label: '护理说明' }]
const formFields = [{ prop: 'recordNo', label: '记录编号' }, { prop: 'recordName', label: '护理老人' }, { prop: 'category', label: '护理类型' }, { prop: 'ownerName', label: '护理人员' }, { prop: 'planTime', label: '护理时间' }, { prop: 'status', label: '护理状态', type: 'select' }, { prop: 'remark', label: '护理说明', type: 'textarea' }]
</script>

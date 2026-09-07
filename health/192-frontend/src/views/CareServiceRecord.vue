<template><DataPage title="服务记录" description="记录编号、服务患者、服务项目、记录人员、服务时间和服务状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'COORDINATOR', 'CAREGIVER', 'FAMILY']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addCareServiceRecord, deleteCareServiceRecord, finishCareServiceRecord, getCareServiceRecordPage, processCareServiceRecord, updateCareServiceRecord } from '../api'
const api = { page: getCareServiceRecordPage, add: addCareServiceRecord, update: updateCareServiceRecord, delete: deleteCareServiceRecord, process: processCareServiceRecord, finish: finishCareServiceRecord }
const columns = [{ prop: 'recordNo', label: '记录编号' }, { prop: 'recordName', label: '服务患者' }, { prop: 'category', label: '服务项目' }, { prop: 'ownerName', label: '记录人员' }, { prop: 'planTime', label: '服务时间' }, { prop: 'status', label: '服务状态' }, { prop: 'remark', label: '服务说明' }]
const formFields = [{ prop: 'recordNo', label: '记录编号' }, { prop: 'recordName', label: '服务患者' }, { prop: 'category', label: '服务项目' }, { prop: 'ownerName', label: '记录人员' }, { prop: 'planTime', label: '服务时间' }, { prop: 'status', label: '服务状态', type: 'select' }, { prop: 'remark', label: '服务说明', type: 'textarea' }]
</script>

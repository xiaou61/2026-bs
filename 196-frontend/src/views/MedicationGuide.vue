<template><DataPage title="用药指导" description="指导编号、购药记录、指导类型、指导药师、指导时间和指导状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'PHARMACY', 'PHARMACIST', 'CLERK', 'CUSTOMER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addMedicationGuide, deleteMedicationGuide, finishMedicationGuide, getMedicationGuidePage, processMedicationGuide, updateMedicationGuide } from '../api'
const api = { page: getMedicationGuidePage, add: addMedicationGuide, update: updateMedicationGuide, delete: deleteMedicationGuide, process: processMedicationGuide, finish: finishMedicationGuide }
const columns = [{ prop: 'recordNo', label: '指导编号' }, { prop: 'recordName', label: '购药记录' }, { prop: 'category', label: '指导类型' }, { prop: 'ownerName', label: '指导药师' }, { prop: 'planTime', label: '指导时间' }, { prop: 'status', label: '指导状态' }, { prop: 'remark', label: '指导说明' }]
const formFields = [{ prop: 'recordNo', label: '指导编号' }, { prop: 'recordName', label: '购药记录' }, { prop: 'category', label: '指导类型' }, { prop: 'ownerName', label: '指导药师' }, { prop: 'planTime', label: '指导时间' }, { prop: 'status', label: '指导状态', type: 'select' }, { prop: 'remark', label: '指导说明', type: 'textarea' }]
</script>

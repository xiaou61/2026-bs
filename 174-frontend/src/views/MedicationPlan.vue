<template><DataPage title="用药方案" description="方案编号、用药患者、药品类型、指导药师、启用时间和方案状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CENTER', 'DOCTOR', 'PHARMACIST', 'RESIDENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addMedicationPlan, deleteMedicationPlan, finishMedicationPlan, getMedicationPlanPage, processMedicationPlan, updateMedicationPlan } from '../api'
const api = { page: getMedicationPlanPage, add: addMedicationPlan, update: updateMedicationPlan, delete: deleteMedicationPlan, process: processMedicationPlan, finish: finishMedicationPlan }
const columns = [{ prop: 'recordNo', label: '方案编号' }, { prop: 'recordName', label: '用药患者' }, { prop: 'category', label: '药品类型' }, { prop: 'ownerName', label: '指导药师' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '方案状态' }, { prop: 'remark', label: '方案说明' }]
const formFields = [{ prop: 'recordNo', label: '方案编号' }, { prop: 'recordName', label: '用药患者' }, { prop: 'category', label: '药品类型' }, { prop: 'ownerName', label: '指导药师' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '方案状态', type: 'select' }, { prop: 'remark', label: '方案说明', type: 'textarea' }]
</script>

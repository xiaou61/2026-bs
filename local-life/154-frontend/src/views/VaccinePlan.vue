<template><DataPage title="疫苗计划" description="计划编号、宠物名称、疫苗类型、负责护士、计划时间和提醒状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'DOCTOR', 'NURSE', 'OWNER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addVaccinePlan, deleteVaccinePlan, finishVaccinePlan, getVaccinePlanPage, processVaccinePlan, updateVaccinePlan } from '../api'
const api = { page: getVaccinePlanPage, add: addVaccinePlan, update: updateVaccinePlan, delete: deleteVaccinePlan, process: processVaccinePlan, finish: finishVaccinePlan }
const columns = [{ prop: 'recordNo', label: '计划编号' }, { prop: 'recordName', label: '宠物名称' }, { prop: 'category', label: '疫苗类型' }, { prop: 'ownerName', label: '负责护士' }, { prop: 'planTime', label: '计划时间' }, { prop: 'status', label: '提醒状态' }, { prop: 'remark', label: '接种说明' }]
const formFields = [{ prop: 'recordNo', label: '计划编号' }, { prop: 'recordName', label: '宠物名称' }, { prop: 'category', label: '疫苗类型' }, { prop: 'ownerName', label: '负责护士' }, { prop: 'planTime', label: '计划时间' }, { prop: 'status', label: '提醒状态', type: 'select' }, { prop: 'remark', label: '接种说明', type: 'textarea' }]
</script>

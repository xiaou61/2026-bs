<template><DataPage title="护理计划" description="计划编号、计划老人、计划类型、护理主管、计划时间和计划状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'NURSING', 'CAREGIVER', 'DIRECTOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addCarePlan, deleteCarePlan, finishCarePlan, getCarePlanPage, processCarePlan, updateCarePlan } from '../api'
const api = { page: getCarePlanPage, add: addCarePlan, update: updateCarePlan, delete: deleteCarePlan, process: processCarePlan, finish: finishCarePlan }
const columns = [{ prop: 'recordNo', label: '计划编号' }, { prop: 'recordName', label: '计划老人' }, { prop: 'category', label: '计划类型' }, { prop: 'ownerName', label: '护理主管' }, { prop: 'planTime', label: '计划时间' }, { prop: 'status', label: '计划状态' }, { prop: 'remark', label: '计划说明' }]
const formFields = [{ prop: 'recordNo', label: '计划编号' }, { prop: 'recordName', label: '计划老人' }, { prop: 'category', label: '计划类型' }, { prop: 'ownerName', label: '护理主管' }, { prop: 'planTime', label: '计划时间' }, { prop: 'status', label: '计划状态', type: 'select' }, { prop: 'remark', label: '计划说明', type: 'textarea' }]
</script>

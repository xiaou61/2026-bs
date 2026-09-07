<template><DataPage title="培养计划" description="计划编号、培养学生、培养阶段、指导导师、计划时间和计划状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'ACADEMIC', 'MENTOR', 'COUNSELOR', 'STUDENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addTrainingPlan, deleteTrainingPlan, finishTrainingPlan, getTrainingPlanPage, processTrainingPlan, updateTrainingPlan } from '../api'
const api = { page: getTrainingPlanPage, add: addTrainingPlan, update: updateTrainingPlan, delete: deleteTrainingPlan, process: processTrainingPlan, finish: finishTrainingPlan }
const columns = [{ prop: 'recordNo', label: '计划编号' }, { prop: 'recordName', label: '培养学生' }, { prop: 'category', label: '培养阶段' }, { prop: 'ownerName', label: '指导导师' }, { prop: 'planTime', label: '计划时间' }, { prop: 'status', label: '计划状态' }, { prop: 'remark', label: '计划说明' }]
const formFields = [{ prop: 'recordNo', label: '计划编号' }, { prop: 'recordName', label: '培养学生' }, { prop: 'category', label: '培养阶段' }, { prop: 'ownerName', label: '指导导师' }, { prop: 'planTime', label: '计划时间' }, { prop: 'status', label: '计划状态', type: 'select' }, { prop: 'remark', label: '计划说明', type: 'textarea' }]
</script>

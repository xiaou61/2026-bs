<template><DataPage title="训练计划" description="计划编号、会员姓名、训练目标、制定教练、计划时间和计划状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CENTER', 'COACH', 'THERAPIST', 'MEMBER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addTrainingPlan, deleteTrainingPlan, finishTrainingPlan, getTrainingPlanPage, processTrainingPlan, updateTrainingPlan } from '../api'
const api = { page: getTrainingPlanPage, add: addTrainingPlan, update: updateTrainingPlan, delete: deleteTrainingPlan, process: processTrainingPlan, finish: finishTrainingPlan }
const columns = [{ prop: 'recordNo', label: '计划编号' }, { prop: 'recordName', label: '会员姓名' }, { prop: 'category', label: '训练目标' }, { prop: 'ownerName', label: '制定教练' }, { prop: 'planTime', label: '计划时间' }, { prop: 'status', label: '计划状态' }, { prop: 'remark', label: '计划说明' }]
const formFields = [{ prop: 'recordNo', label: '计划编号' }, { prop: 'recordName', label: '会员姓名' }, { prop: 'category', label: '训练目标' }, { prop: 'ownerName', label: '制定教练' }, { prop: 'planTime', label: '计划时间' }, { prop: 'status', label: '计划状态', type: 'select' }, { prop: 'remark', label: '计划说明', type: 'textarea' }]
</script>

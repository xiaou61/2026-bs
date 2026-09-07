<template><DataPage title="体测评估" description="评估编号、会员姓名、评估类型、评估人员、评估时间和评估状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CENTER', 'ASSESSOR', 'THERAPIST', 'MEMBER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addFitnessAssessment, deleteFitnessAssessment, finishFitnessAssessment, getFitnessAssessmentPage, processFitnessAssessment, updateFitnessAssessment } from '../api'
const api = { page: getFitnessAssessmentPage, add: addFitnessAssessment, update: updateFitnessAssessment, delete: deleteFitnessAssessment, process: processFitnessAssessment, finish: finishFitnessAssessment }
const columns = [{ prop: 'recordNo', label: '评估编号' }, { prop: 'recordName', label: '会员姓名' }, { prop: 'category', label: '评估类型' }, { prop: 'ownerName', label: '评估人员' }, { prop: 'planTime', label: '评估时间' }, { prop: 'status', label: '评估状态' }, { prop: 'remark', label: '评估说明' }]
const formFields = [{ prop: 'recordNo', label: '评估编号' }, { prop: 'recordName', label: '会员姓名' }, { prop: 'category', label: '评估类型' }, { prop: 'ownerName', label: '评估人员' }, { prop: 'planTime', label: '评估时间' }, { prop: 'status', label: '评估状态', type: 'select' }, { prop: 'remark', label: '评估说明', type: 'textarea' }]
</script>

<template><DataPage title="考试安排" description="考试编号、考试名称、考试类型、考务人员、考试时间和考试状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'TRAINING', 'EXAMINER', 'INSTRUCTOR', 'TRAINEE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addExamPlan, deleteExamPlan, finishExamPlan, getExamPlanPage, processExamPlan, updateExamPlan } from '../api'
const api = { page: getExamPlanPage, add: addExamPlan, update: updateExamPlan, delete: deleteExamPlan, process: processExamPlan, finish: finishExamPlan }
const columns = [{ prop: 'recordNo', label: '考试编号' }, { prop: 'recordName', label: '考试名称' }, { prop: 'category', label: '考试类型' }, { prop: 'ownerName', label: '考务人员' }, { prop: 'planTime', label: '考试时间' }, { prop: 'status', label: '考试状态' }, { prop: 'remark', label: '考试说明' }]
const formFields = [{ prop: 'recordNo', label: '考试编号' }, { prop: 'recordName', label: '考试名称' }, { prop: 'category', label: '考试类型' }, { prop: 'ownerName', label: '考务人员' }, { prop: 'planTime', label: '考试时间' }, { prop: 'status', label: '考试状态', type: 'select' }, { prop: 'remark', label: '考试说明', type: 'textarea' }]
</script>

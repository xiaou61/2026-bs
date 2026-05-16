<template><DataPage title="考试成绩" description="成绩编号、考试科目、成绩类型、评分人员、出分时间和成绩状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'TRAINING', 'EXAMINER', 'INSTRUCTOR', 'TRAINEE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addExamScore, deleteExamScore, finishExamScore, getExamScorePage, processExamScore, updateExamScore } from '../api'
const api = { page: getExamScorePage, add: addExamScore, update: updateExamScore, delete: deleteExamScore, process: processExamScore, finish: finishExamScore }
const columns = [{ prop: 'recordNo', label: '成绩编号' }, { prop: 'recordName', label: '考试科目' }, { prop: 'category', label: '成绩类型' }, { prop: 'ownerName', label: '评分人员' }, { prop: 'planTime', label: '出分时间' }, { prop: 'status', label: '成绩状态' }, { prop: 'remark', label: '成绩说明' }]
const formFields = [{ prop: 'recordNo', label: '成绩编号' }, { prop: 'recordName', label: '考试科目' }, { prop: 'category', label: '成绩类型' }, { prop: 'ownerName', label: '评分人员' }, { prop: 'planTime', label: '出分时间' }, { prop: 'status', label: '成绩状态', type: 'select' }, { prop: 'remark', label: '成绩说明', type: 'textarea' }]
</script>

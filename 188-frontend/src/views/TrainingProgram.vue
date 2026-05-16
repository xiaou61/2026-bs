<template><DataPage title="培训项目" description="项目编号、项目名称、培训类型、负责人、计划时间和项目状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'EDU', 'INSTRUCTOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addTrainingProgram, deleteTrainingProgram, finishTrainingProgram, getTrainingProgramPage, processTrainingProgram, updateTrainingProgram } from '../api'
const api = { page: getTrainingProgramPage, add: addTrainingProgram, update: updateTrainingProgram, delete: deleteTrainingProgram, process: processTrainingProgram, finish: finishTrainingProgram }
const columns = [{ prop: 'recordNo', label: '项目编号' }, { prop: 'recordName', label: '项目名称' }, { prop: 'category', label: '培训类型' }, { prop: 'ownerName', label: '负责人' }, { prop: 'planTime', label: '计划时间' }, { prop: 'status', label: '项目状态' }, { prop: 'remark', label: '项目说明' }]
const formFields = [{ prop: 'recordNo', label: '项目编号' }, { prop: 'recordName', label: '项目名称' }, { prop: 'category', label: '培训类型' }, { prop: 'ownerName', label: '负责人' }, { prop: 'planTime', label: '计划时间' }, { prop: 'status', label: '项目状态', type: 'select' }, { prop: 'remark', label: '项目说明', type: 'textarea' }]
</script>

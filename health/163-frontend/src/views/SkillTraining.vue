<template><DataPage title="技能训练" description="训练编号、训练项目、训练类型、指导老师、训练时间和训练状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'TEACHER', 'STUDENT', 'EXAMINER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addSkillTraining, deleteSkillTraining, finishSkillTraining, getSkillTrainingPage, processSkillTraining, updateSkillTraining } from '../api'
const api = { page: getSkillTrainingPage, add: addSkillTraining, update: updateSkillTraining, delete: deleteSkillTraining, process: processSkillTraining, finish: finishSkillTraining }
const columns = [{ prop: 'recordNo', label: '训练编号' }, { prop: 'recordName', label: '训练项目' }, { prop: 'category', label: '训练类型' }, { prop: 'ownerName', label: '指导老师' }, { prop: 'planTime', label: '训练时间' }, { prop: 'status', label: '训练状态' }, { prop: 'remark', label: '训练说明' }]
const formFields = [{ prop: 'recordNo', label: '训练编号' }, { prop: 'recordName', label: '训练项目' }, { prop: 'category', label: '训练类型' }, { prop: 'ownerName', label: '指导老师' }, { prop: 'planTime', label: '训练时间' }, { prop: 'status', label: '训练状态', type: 'select' }, { prop: 'remark', label: '训练说明', type: 'textarea' }]
</script>

<template><DataPage title="康复训练" description="训练编号、康复计划、训练项目、指导人员、训练时间和训练状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'COMMUNITY', 'THERAPIST']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addRehabTraining, deleteRehabTraining, finishRehabTraining, getRehabTrainingPage, processRehabTraining, updateRehabTraining } from '../api'
const api = { page: getRehabTrainingPage, add: addRehabTraining, update: updateRehabTraining, delete: deleteRehabTraining, process: processRehabTraining, finish: finishRehabTraining }
const columns = [{ prop: 'recordNo', label: '训练编号' }, { prop: 'recordName', label: '康复计划' }, { prop: 'category', label: '训练项目' }, { prop: 'ownerName', label: '指导人员' }, { prop: 'planTime', label: '训练时间' }, { prop: 'status', label: '训练状态' }, { prop: 'remark', label: '训练说明' }]
const formFields = [{ prop: 'recordNo', label: '训练编号' }, { prop: 'recordName', label: '康复计划' }, { prop: 'category', label: '训练项目' }, { prop: 'ownerName', label: '指导人员' }, { prop: 'planTime', label: '训练时间' }, { prop: 'status', label: '训练状态', type: 'select' }, { prop: 'remark', label: '训练说明', type: 'textarea' }]
</script>

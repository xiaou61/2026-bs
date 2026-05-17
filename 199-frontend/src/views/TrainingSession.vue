<template><DataPage title="训练安排" description="安排编号、训练计划、训练类型、指导教练、训练时间和训练状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CENTER', 'COACH', 'THERAPIST', 'MEMBER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addTrainingSession, deleteTrainingSession, finishTrainingSession, getTrainingSessionPage, processTrainingSession, updateTrainingSession } from '../api'
const api = { page: getTrainingSessionPage, add: addTrainingSession, update: updateTrainingSession, delete: deleteTrainingSession, process: processTrainingSession, finish: finishTrainingSession }
const columns = [{ prop: 'recordNo', label: '安排编号' }, { prop: 'recordName', label: '训练计划' }, { prop: 'category', label: '训练类型' }, { prop: 'ownerName', label: '指导教练' }, { prop: 'planTime', label: '训练时间' }, { prop: 'status', label: '训练状态' }, { prop: 'remark', label: '安排说明' }]
const formFields = [{ prop: 'recordNo', label: '安排编号' }, { prop: 'recordName', label: '训练计划' }, { prop: 'category', label: '训练类型' }, { prop: 'ownerName', label: '指导教练' }, { prop: 'planTime', label: '训练时间' }, { prop: 'status', label: '训练状态', type: 'select' }, { prop: 'remark', label: '安排说明', type: 'textarea' }]
</script>

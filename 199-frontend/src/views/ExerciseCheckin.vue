<template><DataPage title="训练打卡" description="打卡编号、训练安排、打卡类型、记录人员、打卡时间和打卡状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CENTER', 'COACH', 'MEMBER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addExerciseCheckin, deleteExerciseCheckin, finishExerciseCheckin, getExerciseCheckinPage, processExerciseCheckin, updateExerciseCheckin } from '../api'
const api = { page: getExerciseCheckinPage, add: addExerciseCheckin, update: updateExerciseCheckin, delete: deleteExerciseCheckin, process: processExerciseCheckin, finish: finishExerciseCheckin }
const columns = [{ prop: 'recordNo', label: '打卡编号' }, { prop: 'recordName', label: '训练安排' }, { prop: 'category', label: '打卡类型' }, { prop: 'ownerName', label: '记录人员' }, { prop: 'planTime', label: '打卡时间' }, { prop: 'status', label: '打卡状态' }, { prop: 'remark', label: '打卡说明' }]
const formFields = [{ prop: 'recordNo', label: '打卡编号' }, { prop: 'recordName', label: '训练安排' }, { prop: 'category', label: '打卡类型' }, { prop: 'ownerName', label: '记录人员' }, { prop: 'planTime', label: '打卡时间' }, { prop: 'status', label: '打卡状态', type: 'select' }, { prop: 'remark', label: '打卡说明', type: 'textarea' }]
</script>

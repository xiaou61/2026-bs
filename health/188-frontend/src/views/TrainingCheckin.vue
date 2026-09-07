<template><DataPage title="培训签到" description="签到编号、关联班次、签到类型、签到人员、签到时间和签到状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'EDU', 'INSTRUCTOR', 'NURSE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addTrainingCheckin, deleteTrainingCheckin, finishTrainingCheckin, getTrainingCheckinPage, processTrainingCheckin, updateTrainingCheckin } from '../api'
const api = { page: getTrainingCheckinPage, add: addTrainingCheckin, update: updateTrainingCheckin, delete: deleteTrainingCheckin, process: processTrainingCheckin, finish: finishTrainingCheckin }
const columns = [{ prop: 'recordNo', label: '签到编号' }, { prop: 'recordName', label: '关联班次' }, { prop: 'category', label: '签到类型' }, { prop: 'ownerName', label: '签到人员' }, { prop: 'planTime', label: '签到时间' }, { prop: 'status', label: '签到状态' }, { prop: 'remark', label: '签到说明' }]
const formFields = [{ prop: 'recordNo', label: '签到编号' }, { prop: 'recordName', label: '关联班次' }, { prop: 'category', label: '签到类型' }, { prop: 'ownerName', label: '签到人员' }, { prop: 'planTime', label: '签到时间' }, { prop: 'status', label: '签到状态', type: 'select' }, { prop: 'remark', label: '签到说明', type: 'textarea' }]
</script>

<template><DataPage title="培训班次" description="班次编号、班次名称、培训类型、带教老师、开班时间和班次状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'EDU', 'INSTRUCTOR', 'NURSE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addTrainingClass, deleteTrainingClass, finishTrainingClass, getTrainingClassPage, processTrainingClass, updateTrainingClass } from '../api'
const api = { page: getTrainingClassPage, add: addTrainingClass, update: updateTrainingClass, delete: deleteTrainingClass, process: processTrainingClass, finish: finishTrainingClass }
const columns = [{ prop: 'recordNo', label: '班次编号' }, { prop: 'recordName', label: '班次名称' }, { prop: 'category', label: '培训类型' }, { prop: 'ownerName', label: '带教老师' }, { prop: 'planTime', label: '开班时间' }, { prop: 'status', label: '班次状态' }, { prop: 'remark', label: '班次说明' }]
const formFields = [{ prop: 'recordNo', label: '班次编号' }, { prop: 'recordName', label: '班次名称' }, { prop: 'category', label: '培训类型' }, { prop: 'ownerName', label: '带教老师' }, { prop: 'planTime', label: '开班时间' }, { prop: 'status', label: '班次状态', type: 'select' }, { prop: 'remark', label: '班次说明', type: 'textarea' }]
</script>

<template><DataPage title="带教评分" description="评分编号、评分对象、评分维度、评分人、评分时间和评分状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'SECRETARY', 'TEACHER', 'STUDENT', 'DIRECTOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addTeachingScore, deleteTeachingScore, finishTeachingScore, getTeachingScorePage, processTeachingScore, updateTeachingScore } from '../api'
const api = { page: getTeachingScorePage, add: addTeachingScore, update: updateTeachingScore, delete: deleteTeachingScore, process: processTeachingScore, finish: finishTeachingScore }
const columns = [{ prop: 'recordNo', label: '评分编号' }, { prop: 'recordName', label: '评分对象' }, { prop: 'category', label: '评分维度' }, { prop: 'ownerName', label: '评分人' }, { prop: 'planTime', label: '评分时间' }, { prop: 'status', label: '评分状态' }, { prop: 'remark', label: '评分说明' }]
const formFields = [{ prop: 'recordNo', label: '评分编号' }, { prop: 'recordName', label: '评分对象' }, { prop: 'category', label: '评分维度' }, { prop: 'ownerName', label: '评分人' }, { prop: 'planTime', label: '评分时间' }, { prop: 'status', label: '评分状态', type: 'select' }, { prop: 'remark', label: '评分说明', type: 'textarea' }]
</script>

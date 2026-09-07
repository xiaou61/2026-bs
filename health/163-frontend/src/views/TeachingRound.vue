<template><DataPage title="教学查房" description="查房编号、查房主题、查房类型、主持老师、查房时间和查房状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'SECRETARY', 'DIRECTOR', 'TEACHER', 'STUDENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addTeachingRound, deleteTeachingRound, finishTeachingRound, getTeachingRoundPage, processTeachingRound, updateTeachingRound } from '../api'
const api = { page: getTeachingRoundPage, add: addTeachingRound, update: updateTeachingRound, delete: deleteTeachingRound, process: processTeachingRound, finish: finishTeachingRound }
const columns = [{ prop: 'recordNo', label: '查房编号' }, { prop: 'recordName', label: '查房主题' }, { prop: 'category', label: '查房类型' }, { prop: 'ownerName', label: '主持老师' }, { prop: 'planTime', label: '查房时间' }, { prop: 'status', label: '查房状态' }, { prop: 'remark', label: '查房说明' }]
const formFields = [{ prop: 'recordNo', label: '查房编号' }, { prop: 'recordName', label: '查房主题' }, { prop: 'category', label: '查房类型' }, { prop: 'ownerName', label: '主持老师' }, { prop: 'planTime', label: '查房时间' }, { prop: 'status', label: '查房状态', type: 'select' }, { prop: 'remark', label: '查房说明', type: 'textarea' }]
</script>

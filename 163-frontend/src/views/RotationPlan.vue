<template><DataPage title="轮转安排" description="轮转编号、轮转学生、轮转科室、带教老师、开始时间和轮转状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'SECRETARY', 'DIRECTOR', 'TEACHER', 'STUDENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addRotationPlan, deleteRotationPlan, finishRotationPlan, getRotationPlanPage, processRotationPlan, updateRotationPlan } from '../api'
const api = { page: getRotationPlanPage, add: addRotationPlan, update: updateRotationPlan, delete: deleteRotationPlan, process: processRotationPlan, finish: finishRotationPlan }
const columns = [{ prop: 'recordNo', label: '轮转编号' }, { prop: 'recordName', label: '轮转学生' }, { prop: 'category', label: '轮转科室' }, { prop: 'ownerName', label: '带教老师' }, { prop: 'planTime', label: '开始时间' }, { prop: 'status', label: '轮转状态' }, { prop: 'remark', label: '轮转说明' }]
const formFields = [{ prop: 'recordNo', label: '轮转编号' }, { prop: 'recordName', label: '轮转学生' }, { prop: 'category', label: '轮转科室' }, { prop: 'ownerName', label: '带教老师' }, { prop: 'planTime', label: '开始时间' }, { prop: 'status', label: '轮转状态', type: 'select' }, { prop: 'remark', label: '轮转说明', type: 'textarea' }]
</script>

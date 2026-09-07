<template><DataPage title="康复计划" description="计划编号、服务居民、康复类型、治疗师、计划时间和计划状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'COMMUNITY', 'THERAPIST', 'RESIDENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addRehabPlan, deleteRehabPlan, finishRehabPlan, getRehabPlanPage, processRehabPlan, updateRehabPlan } from '../api'
const api = { page: getRehabPlanPage, add: addRehabPlan, update: updateRehabPlan, delete: deleteRehabPlan, process: processRehabPlan, finish: finishRehabPlan }
const columns = [{ prop: 'recordNo', label: '计划编号' }, { prop: 'recordName', label: '服务居民' }, { prop: 'category', label: '康复类型' }, { prop: 'ownerName', label: '治疗师' }, { prop: 'planTime', label: '计划时间' }, { prop: 'status', label: '计划状态' }, { prop: 'remark', label: '计划说明' }]
const formFields = [{ prop: 'recordNo', label: '计划编号' }, { prop: 'recordName', label: '服务居民' }, { prop: 'category', label: '康复类型' }, { prop: 'ownerName', label: '治疗师' }, { prop: 'planTime', label: '计划时间' }, { prop: 'status', label: '计划状态', type: 'select' }, { prop: 'remark', label: '计划说明', type: 'textarea' }]
</script>

<template><DataPage title="团建行程" description="行程编号、行程名称、行程类型、策划人员、出行时间和行程状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'AGENCY', 'PLANNER', 'LEADER', 'PARTICIPANT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addTripPlan, deleteTripPlan, finishTripPlan, getTripPlanPage, processTripPlan, updateTripPlan } from '../api'
const api = { page: getTripPlanPage, add: addTripPlan, update: updateTripPlan, delete: deleteTripPlan, process: processTripPlan, finish: finishTripPlan }
const columns = [{ prop: 'recordNo', label: '行程编号' }, { prop: 'recordName', label: '行程名称' }, { prop: 'category', label: '行程类型' }, { prop: 'ownerName', label: '策划人员' }, { prop: 'planTime', label: '出行时间' }, { prop: 'status', label: '行程状态' }, { prop: 'remark', label: '行程说明' }]
const formFields = [{ prop: 'recordNo', label: '行程编号' }, { prop: 'recordName', label: '行程名称' }, { prop: 'category', label: '行程类型' }, { prop: 'ownerName', label: '策划人员' }, { prop: 'planTime', label: '出行时间' }, { prop: 'status', label: '行程状态', type: 'select' }, { prop: 'remark', label: '行程说明', type: 'textarea' }]
</script>

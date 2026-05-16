<template><DataPage title="时段计划" description="时段编号、时段名称、作业类型、计划人、计划时间和开放状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'DISPATCHER', 'YARDMASTER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addTimeSlotPlan, deleteTimeSlotPlan, finishTimeSlotPlan, getTimeSlotPlanPage, processTimeSlotPlan, updateTimeSlotPlan } from '../api'
const api = { page: getTimeSlotPlanPage, add: addTimeSlotPlan, update: updateTimeSlotPlan, delete: deleteTimeSlotPlan, process: processTimeSlotPlan, finish: finishTimeSlotPlan }
const columns = [{ prop: 'recordNo', label: '时段编号' }, { prop: 'recordName', label: '时段名称' }, { prop: 'category', label: '作业类型' }, { prop: 'ownerName', label: '计划人' }, { prop: 'planTime', label: '计划时间' }, { prop: 'status', label: '开放状态' }, { prop: 'remark', label: '时段说明' }]
const formFields = [{ prop: 'recordNo', label: '时段编号' }, { prop: 'recordName', label: '时段名称' }, { prop: 'category', label: '作业类型' }, { prop: 'ownerName', label: '计划人' }, { prop: 'planTime', label: '计划时间' }, { prop: 'status', label: '开放状态', type: 'select' }, { prop: 'remark', label: '时段说明', type: 'textarea' }]
</script>

<template><DataPage title="巡检计划" description="计划编号、巡检区域、巡检类型、巡检人员、计划时间和计划状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'SANITATION', 'INSPECTOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addInspectionPlan, deleteInspectionPlan, finishInspectionPlan, getInspectionPlanPage, processInspectionPlan, updateInspectionPlan } from '../api'
const api = { page: getInspectionPlanPage, add: addInspectionPlan, update: updateInspectionPlan, delete: deleteInspectionPlan, process: processInspectionPlan, finish: finishInspectionPlan }
const columns = [{ prop: 'recordNo', label: '计划编号' }, { prop: 'recordName', label: '巡检区域' }, { prop: 'category', label: '巡检类型' }, { prop: 'ownerName', label: '巡检人员' }, { prop: 'planTime', label: '计划时间' }, { prop: 'status', label: '计划状态' }, { prop: 'remark', label: '计划说明' }]
const formFields = [{ prop: 'recordNo', label: '计划编号' }, { prop: 'recordName', label: '巡检区域' }, { prop: 'category', label: '巡检类型' }, { prop: 'ownerName', label: '巡检人员' }, { prop: 'planTime', label: '计划时间' }, { prop: 'status', label: '计划状态', type: 'select' }, { prop: 'remark', label: '计划说明', type: 'textarea' }]
</script>

<template><DataPage title="随访计划" description="计划编号、随访患者、随访类型、责任人员、计划时间和计划状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CENTER', 'DOCTOR', 'NURSE', 'RESIDENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addFollowupPlan, deleteFollowupPlan, finishFollowupPlan, getFollowupPlanPage, processFollowupPlan, updateFollowupPlan } from '../api'
const api = { page: getFollowupPlanPage, add: addFollowupPlan, update: updateFollowupPlan, delete: deleteFollowupPlan, process: processFollowupPlan, finish: finishFollowupPlan }
const columns = [{ prop: 'recordNo', label: '计划编号' }, { prop: 'recordName', label: '随访患者' }, { prop: 'category', label: '随访类型' }, { prop: 'ownerName', label: '责任人员' }, { prop: 'planTime', label: '计划时间' }, { prop: 'status', label: '计划状态' }, { prop: 'remark', label: '计划说明' }]
const formFields = [{ prop: 'recordNo', label: '计划编号' }, { prop: 'recordName', label: '随访患者' }, { prop: 'category', label: '随访类型' }, { prop: 'ownerName', label: '责任人员' }, { prop: 'planTime', label: '计划时间' }, { prop: 'status', label: '计划状态', type: 'select' }, { prop: 'remark', label: '计划说明', type: 'textarea' }]
</script>

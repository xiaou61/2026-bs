<template><DataPage title="直播计划" description="计划编号、计划主题、直播类型、负责人、计划时间和计划状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'BASE', 'ANCHOR', 'SELECTOR', 'MERCHANT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addLivePlan, deleteLivePlan, finishLivePlan, getLivePlanPage, processLivePlan, updateLivePlan } from '../api'
const api = { page: getLivePlanPage, add: addLivePlan, update: updateLivePlan, delete: deleteLivePlan, process: processLivePlan, finish: finishLivePlan }
const columns = [{ prop: 'recordNo', label: '计划编号' }, { prop: 'recordName', label: '计划主题' }, { prop: 'category', label: '直播类型' }, { prop: 'ownerName', label: '负责人' }, { prop: 'planTime', label: '计划时间' }, { prop: 'status', label: '计划状态' }, { prop: 'remark', label: '计划说明' }]
const formFields = [{ prop: 'recordNo', label: '计划编号' }, { prop: 'recordName', label: '计划主题' }, { prop: 'category', label: '直播类型' }, { prop: 'ownerName', label: '负责人' }, { prop: 'planTime', label: '计划时间' }, { prop: 'status', label: '计划状态', type: 'select' }, { prop: 'remark', label: '计划说明', type: 'textarea' }]
</script>

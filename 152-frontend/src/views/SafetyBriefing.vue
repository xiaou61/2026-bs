<template><DataPage title="安全交底" description="交底编号、作业项目、交底类型、交底人、交底时间和确认状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'SAFETY', 'GUARDIAN', 'WORKER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addSafetyBriefing, deleteSafetyBriefing, finishSafetyBriefing, getSafetyBriefingPage, processSafetyBriefing, updateSafetyBriefing } from '../api'
const api = { page: getSafetyBriefingPage, add: addSafetyBriefing, update: updateSafetyBriefing, delete: deleteSafetyBriefing, process: processSafetyBriefing, finish: finishSafetyBriefing }
const columns = [{ prop: 'recordNo', label: '交底编号' }, { prop: 'recordName', label: '作业项目' }, { prop: 'category', label: '交底类型' }, { prop: 'ownerName', label: '交底人' }, { prop: 'planTime', label: '交底时间' }, { prop: 'status', label: '确认状态' }, { prop: 'remark', label: '交底内容' }]
const formFields = [{ prop: 'recordNo', label: '交底编号' }, { prop: 'recordName', label: '作业项目' }, { prop: 'category', label: '交底类型' }, { prop: 'ownerName', label: '交底人' }, { prop: 'planTime', label: '交底时间' }, { prop: 'status', label: '确认状态', type: 'select' }, { prop: 'remark', label: '交底内容', type: 'textarea' }]
</script>

<template><DataPage title="实操记录" description="实操编号、实操项目、实操类型、指导老师、实操时间和记录状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'INSTRUCTOR', 'ASSESSOR', 'NURSE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addPracticeRecord, deletePracticeRecord, finishPracticeRecord, getPracticeRecordPage, processPracticeRecord, updatePracticeRecord } from '../api'
const api = { page: getPracticeRecordPage, add: addPracticeRecord, update: updatePracticeRecord, delete: deletePracticeRecord, process: processPracticeRecord, finish: finishPracticeRecord }
const columns = [{ prop: 'recordNo', label: '实操编号' }, { prop: 'recordName', label: '实操项目' }, { prop: 'category', label: '实操类型' }, { prop: 'ownerName', label: '指导老师' }, { prop: 'planTime', label: '实操时间' }, { prop: 'status', label: '记录状态' }, { prop: 'remark', label: '实操说明' }]
const formFields = [{ prop: 'recordNo', label: '实操编号' }, { prop: 'recordName', label: '实操项目' }, { prop: 'category', label: '实操类型' }, { prop: 'ownerName', label: '指导老师' }, { prop: 'planTime', label: '实操时间' }, { prop: 'status', label: '记录状态', type: 'select' }, { prop: 'remark', label: '实操说明', type: 'textarea' }]
</script>

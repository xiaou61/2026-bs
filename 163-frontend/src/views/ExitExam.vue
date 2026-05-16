<template><DataPage title="出科考核" description="考核编号、考核学生、考核类型、考核评委、考核时间和考核状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'SECRETARY', 'EXAMINER', 'TEACHER', 'STUDENT', 'DIRECTOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addExitExam, deleteExitExam, finishExitExam, getExitExamPage, processExitExam, updateExitExam } from '../api'
const api = { page: getExitExamPage, add: addExitExam, update: updateExitExam, delete: deleteExitExam, process: processExitExam, finish: finishExitExam }
const columns = [{ prop: 'recordNo', label: '考核编号' }, { prop: 'recordName', label: '考核学生' }, { prop: 'category', label: '考核类型' }, { prop: 'ownerName', label: '考核评委' }, { prop: 'planTime', label: '考核时间' }, { prop: 'status', label: '考核状态' }, { prop: 'remark', label: '考核说明' }]
const formFields = [{ prop: 'recordNo', label: '考核编号' }, { prop: 'recordName', label: '考核学生' }, { prop: 'category', label: '考核类型' }, { prop: 'ownerName', label: '考核评委' }, { prop: 'planTime', label: '考核时间' }, { prop: 'status', label: '考核状态', type: 'select' }, { prop: 'remark', label: '考核说明', type: 'textarea' }]
</script>

<template><DataPage title="学习记录" description="记录编号、学习病例、学习类型、实习学生、学习时间和完成状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'TEACHER', 'STUDENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addStudyRecord, deleteStudyRecord, finishStudyRecord, getStudyRecordPage, processStudyRecord, updateStudyRecord } from '../api'
const api = { page: getStudyRecordPage, add: addStudyRecord, update: updateStudyRecord, delete: deleteStudyRecord, process: processStudyRecord, finish: finishStudyRecord }
const columns = [{ prop: 'recordNo', label: '记录编号' }, { prop: 'recordName', label: '学习病例' }, { prop: 'category', label: '学习类型' }, { prop: 'ownerName', label: '实习学生' }, { prop: 'planTime', label: '学习时间' }, { prop: 'status', label: '完成状态' }, { prop: 'remark', label: '学习说明' }]
const formFields = [{ prop: 'recordNo', label: '记录编号' }, { prop: 'recordName', label: '学习病例' }, { prop: 'category', label: '学习类型' }, { prop: 'ownerName', label: '实习学生' }, { prop: 'planTime', label: '学习时间' }, { prop: 'status', label: '完成状态', type: 'select' }, { prop: 'remark', label: '学习说明', type: 'textarea' }]
</script>

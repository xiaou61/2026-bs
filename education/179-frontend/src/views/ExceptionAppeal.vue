<template><DataPage title="异常申诉" description="申诉编号、考勤记录、申诉类型、申诉学生、申诉时间和申诉状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'ACADEMIC', 'TEACHER', 'COUNSELOR', 'STUDENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addExceptionAppeal, deleteExceptionAppeal, finishExceptionAppeal, getExceptionAppealPage, processExceptionAppeal, updateExceptionAppeal } from '../api'
const api = { page: getExceptionAppealPage, add: addExceptionAppeal, update: updateExceptionAppeal, delete: deleteExceptionAppeal, process: processExceptionAppeal, finish: finishExceptionAppeal }
const columns = [{ prop: 'recordNo', label: '申诉编号' }, { prop: 'recordName', label: '考勤记录' }, { prop: 'category', label: '申诉类型' }, { prop: 'ownerName', label: '申诉学生' }, { prop: 'planTime', label: '申诉时间' }, { prop: 'status', label: '申诉状态' }, { prop: 'remark', label: '申诉说明' }]
const formFields = [{ prop: 'recordNo', label: '申诉编号' }, { prop: 'recordName', label: '考勤记录' }, { prop: 'category', label: '申诉类型' }, { prop: 'ownerName', label: '申诉学生' }, { prop: 'planTime', label: '申诉时间' }, { prop: 'status', label: '申诉状态', type: 'select' }, { prop: 'remark', label: '申诉说明', type: 'textarea' }]
</script>

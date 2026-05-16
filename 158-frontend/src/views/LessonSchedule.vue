<template><DataPage title="排课计划" description="排课编号、课程班级、排课类型、授课老师、上课时间和排课状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'PRINCIPAL', 'ACADEMIC', 'TEACHER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addLessonSchedule, deleteLessonSchedule, finishLessonSchedule, getLessonSchedulePage, processLessonSchedule, updateLessonSchedule } from '../api'
const api = { page: getLessonSchedulePage, add: addLessonSchedule, update: updateLessonSchedule, delete: deleteLessonSchedule, process: processLessonSchedule, finish: finishLessonSchedule }
const columns = [{ prop: 'recordNo', label: '排课编号' }, { prop: 'recordName', label: '课程班级' }, { prop: 'category', label: '排课类型' }, { prop: 'ownerName', label: '授课老师' }, { prop: 'planTime', label: '上课时间' }, { prop: 'status', label: '排课状态' }, { prop: 'remark', label: '排课说明' }]
const formFields = [{ prop: 'recordNo', label: '排课编号' }, { prop: 'recordName', label: '课程班级' }, { prop: 'category', label: '排课类型' }, { prop: 'ownerName', label: '授课老师' }, { prop: 'planTime', label: '上课时间' }, { prop: 'status', label: '排课状态', type: 'select' }, { prop: 'remark', label: '排课说明', type: 'textarea' }]
</script>

<template><DataPage title="课程排课" description="排课编号、课程名称、排课类型、任课教师、上课时间和排课状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'ACADEMIC', 'TEACHER', 'STUDENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addCourseSchedule, deleteCourseSchedule, finishCourseSchedule, getCourseSchedulePage, processCourseSchedule, updateCourseSchedule } from '../api'
const api = { page: getCourseSchedulePage, add: addCourseSchedule, update: updateCourseSchedule, delete: deleteCourseSchedule, process: processCourseSchedule, finish: finishCourseSchedule }
const columns = [{ prop: 'recordNo', label: '排课编号' }, { prop: 'recordName', label: '课程名称' }, { prop: 'category', label: '排课类型' }, { prop: 'ownerName', label: '任课教师' }, { prop: 'planTime', label: '上课时间' }, { prop: 'status', label: '排课状态' }, { prop: 'remark', label: '排课说明' }]
const formFields = [{ prop: 'recordNo', label: '排课编号' }, { prop: 'recordName', label: '课程名称' }, { prop: 'category', label: '排课类型' }, { prop: 'ownerName', label: '任课教师' }, { prop: 'planTime', label: '上课时间' }, { prop: 'status', label: '排课状态', type: 'select' }, { prop: 'remark', label: '排课说明', type: 'textarea' }]
</script>

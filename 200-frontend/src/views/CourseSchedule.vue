<template><DataPage title="工坊排期" description="排期编号、课程名称、排期类型、授课讲师、开课时间和排期状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'WORKSHOP', 'TEACHER', 'VISITOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addCourseSchedule, deleteCourseSchedule, finishCourseSchedule, getCourseSchedulePage, processCourseSchedule, updateCourseSchedule } from '../api'
const api = { page: getCourseSchedulePage, add: addCourseSchedule, update: updateCourseSchedule, delete: deleteCourseSchedule, process: processCourseSchedule, finish: finishCourseSchedule }
const columns = [{ prop: 'recordNo', label: '排期编号' }, { prop: 'recordName', label: '课程名称' }, { prop: 'category', label: '排期类型' }, { prop: 'ownerName', label: '授课讲师' }, { prop: 'planTime', label: '开课时间' }, { prop: 'status', label: '排期状态' }, { prop: 'remark', label: '排期说明' }]
const formFields = [{ prop: 'recordNo', label: '排期编号' }, { prop: 'recordName', label: '课程名称' }, { prop: 'category', label: '排期类型' }, { prop: 'ownerName', label: '授课讲师' }, { prop: 'planTime', label: '开课时间' }, { prop: 'status', label: '排期状态', type: 'select' }, { prop: 'remark', label: '排期说明', type: 'textarea' }]
</script>

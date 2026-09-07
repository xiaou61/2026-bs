<template><DataPage title="培训课程" description="课程编号、课程名称、课程类型、授课讲师、开课时间和课程状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'TRAINING', 'INSTRUCTOR', 'TRAINEE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addTrainingCourse, deleteTrainingCourse, finishTrainingCourse, getTrainingCoursePage, processTrainingCourse, updateTrainingCourse } from '../api'
const api = { page: getTrainingCoursePage, add: addTrainingCourse, update: updateTrainingCourse, delete: deleteTrainingCourse, process: processTrainingCourse, finish: finishTrainingCourse }
const columns = [{ prop: 'recordNo', label: '课程编号' }, { prop: 'recordName', label: '课程名称' }, { prop: 'category', label: '课程类型' }, { prop: 'ownerName', label: '授课讲师' }, { prop: 'planTime', label: '开课时间' }, { prop: 'status', label: '课程状态' }, { prop: 'remark', label: '课程说明' }]
const formFields = [{ prop: 'recordNo', label: '课程编号' }, { prop: 'recordName', label: '课程名称' }, { prop: 'category', label: '课程类型' }, { prop: 'ownerName', label: '授课讲师' }, { prop: 'planTime', label: '开课时间' }, { prop: 'status', label: '课程状态', type: 'select' }, { prop: 'remark', label: '课程说明', type: 'textarea' }]
</script>

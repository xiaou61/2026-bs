<template><DataPage title="课程报名" description="报名编号、报名课程、报名类型、报名学员、报名时间和报名状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'TRAINING', 'TRAINEE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addCourseEnrollment, deleteCourseEnrollment, finishCourseEnrollment, getCourseEnrollmentPage, processCourseEnrollment, updateCourseEnrollment } from '../api'
const api = { page: getCourseEnrollmentPage, add: addCourseEnrollment, update: updateCourseEnrollment, delete: deleteCourseEnrollment, process: processCourseEnrollment, finish: finishCourseEnrollment }
const columns = [{ prop: 'recordNo', label: '报名编号' }, { prop: 'recordName', label: '报名课程' }, { prop: 'category', label: '报名类型' }, { prop: 'ownerName', label: '报名学员' }, { prop: 'planTime', label: '报名时间' }, { prop: 'status', label: '报名状态' }, { prop: 'remark', label: '报名说明' }]
const formFields = [{ prop: 'recordNo', label: '报名编号' }, { prop: 'recordName', label: '报名课程' }, { prop: 'category', label: '报名类型' }, { prop: 'ownerName', label: '报名学员' }, { prop: 'planTime', label: '报名时间' }, { prop: 'status', label: '报名状态', type: 'select' }, { prop: 'remark', label: '报名说明', type: 'textarea' }]
</script>

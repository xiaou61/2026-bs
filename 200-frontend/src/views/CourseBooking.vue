<template><DataPage title="课程预约" description="预约编号、课程名称、预约类型、预约学员、预约时间和预约状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'WORKSHOP', 'TEACHER', 'VISITOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addCourseBooking, deleteCourseBooking, finishCourseBooking, getCourseBookingPage, processCourseBooking, updateCourseBooking } from '../api'
const api = { page: getCourseBookingPage, add: addCourseBooking, update: updateCourseBooking, delete: deleteCourseBooking, process: processCourseBooking, finish: finishCourseBooking }
const columns = [{ prop: 'recordNo', label: '预约编号' }, { prop: 'recordName', label: '课程名称' }, { prop: 'category', label: '预约类型' }, { prop: 'ownerName', label: '预约学员' }, { prop: 'planTime', label: '预约时间' }, { prop: 'status', label: '预约状态' }, { prop: 'remark', label: '预约说明' }]
const formFields = [{ prop: 'recordNo', label: '预约编号' }, { prop: 'recordName', label: '课程名称' }, { prop: 'category', label: '预约类型' }, { prop: 'ownerName', label: '预约学员' }, { prop: 'planTime', label: '预约时间' }, { prop: 'status', label: '预约状态', type: 'select' }, { prop: 'remark', label: '预约说明', type: 'textarea' }]
</script>

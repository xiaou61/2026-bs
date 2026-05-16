<template><DataPage title="考勤记录" description="考勤编号、课程班级、考勤类型、任课教师、考勤时间和考勤状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'ACADEMIC', 'TEACHER', 'COUNSELOR', 'STUDENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addAttendanceRecord, deleteAttendanceRecord, finishAttendanceRecord, getAttendanceRecordPage, processAttendanceRecord, updateAttendanceRecord } from '../api'
const api = { page: getAttendanceRecordPage, add: addAttendanceRecord, update: updateAttendanceRecord, delete: deleteAttendanceRecord, process: processAttendanceRecord, finish: finishAttendanceRecord }
const columns = [{ prop: 'recordNo', label: '考勤编号' }, { prop: 'recordName', label: '课程班级' }, { prop: 'category', label: '考勤类型' }, { prop: 'ownerName', label: '任课教师' }, { prop: 'planTime', label: '考勤时间' }, { prop: 'status', label: '考勤状态' }, { prop: 'remark', label: '考勤说明' }]
const formFields = [{ prop: 'recordNo', label: '考勤编号' }, { prop: 'recordName', label: '课程班级' }, { prop: 'category', label: '考勤类型' }, { prop: 'ownerName', label: '任课教师' }, { prop: 'planTime', label: '考勤时间' }, { prop: 'status', label: '考勤状态', type: 'select' }, { prop: 'remark', label: '考勤说明', type: 'textarea' }]
</script>

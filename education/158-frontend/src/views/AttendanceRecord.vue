<template><DataPage title="上课考勤" description="考勤编号、上课班级、考勤类型、点名老师、考勤时间和考勤状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'ACADEMIC', 'TEACHER', 'PARENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addAttendanceRecord, deleteAttendanceRecord, finishAttendanceRecord, getAttendanceRecordPage, processAttendanceRecord, updateAttendanceRecord } from '../api'
const api = { page: getAttendanceRecordPage, add: addAttendanceRecord, update: updateAttendanceRecord, delete: deleteAttendanceRecord, process: processAttendanceRecord, finish: finishAttendanceRecord }
const columns = [{ prop: 'recordNo', label: '考勤编号' }, { prop: 'recordName', label: '上课班级' }, { prop: 'category', label: '考勤类型' }, { prop: 'ownerName', label: '点名老师' }, { prop: 'planTime', label: '考勤时间' }, { prop: 'status', label: '考勤状态' }, { prop: 'remark', label: '考勤说明' }]
const formFields = [{ prop: 'recordNo', label: '考勤编号' }, { prop: 'recordName', label: '上课班级' }, { prop: 'category', label: '考勤类型' }, { prop: 'ownerName', label: '点名老师' }, { prop: 'planTime', label: '考勤时间' }, { prop: 'status', label: '考勤状态', type: 'select' }, { prop: 'remark', label: '考勤说明', type: 'textarea' }]
</script>

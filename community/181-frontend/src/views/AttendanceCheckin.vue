<template><DataPage title="签到记录" description="签到编号、托管儿童、签到类型、签到老师、签到时间和签到状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CENTER', 'TEACHER', 'PARENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addAttendanceCheckin, deleteAttendanceCheckin, finishAttendanceCheckin, getAttendanceCheckinPage, processAttendanceCheckin, updateAttendanceCheckin } from '../api'
const api = { page: getAttendanceCheckinPage, add: addAttendanceCheckin, update: updateAttendanceCheckin, delete: deleteAttendanceCheckin, process: processAttendanceCheckin, finish: finishAttendanceCheckin }
const columns = [{ prop: 'recordNo', label: '签到编号' }, { prop: 'recordName', label: '托管儿童' }, { prop: 'category', label: '签到类型' }, { prop: 'ownerName', label: '签到老师' }, { prop: 'planTime', label: '签到时间' }, { prop: 'status', label: '签到状态' }, { prop: 'remark', label: '签到说明' }]
const formFields = [{ prop: 'recordNo', label: '签到编号' }, { prop: 'recordName', label: '托管儿童' }, { prop: 'category', label: '签到类型' }, { prop: 'ownerName', label: '签到老师' }, { prop: 'planTime', label: '签到时间' }, { prop: 'status', label: '签到状态', type: 'select' }, { prop: 'remark', label: '签到说明', type: 'textarea' }]
</script>

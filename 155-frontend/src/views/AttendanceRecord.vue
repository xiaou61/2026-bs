<template><DataPage title="签到记录" description="签到编号、关联活动、签到方式、签到人、签到时间和签到状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'ORGANIZER', 'VOLUNTEER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addAttendanceRecord, deleteAttendanceRecord, finishAttendanceRecord, getAttendanceRecordPage, processAttendanceRecord, updateAttendanceRecord } from '../api'
const api = { page: getAttendanceRecordPage, add: addAttendanceRecord, update: updateAttendanceRecord, delete: deleteAttendanceRecord, process: processAttendanceRecord, finish: finishAttendanceRecord }
const columns = [{ prop: 'recordNo', label: '签到编号' }, { prop: 'recordName', label: '关联活动' }, { prop: 'category', label: '签到方式' }, { prop: 'ownerName', label: '签到人' }, { prop: 'planTime', label: '签到时间' }, { prop: 'status', label: '签到状态' }, { prop: 'remark', label: '签到说明' }]
const formFields = [{ prop: 'recordNo', label: '签到编号' }, { prop: 'recordName', label: '关联活动' }, { prop: 'category', label: '签到方式' }, { prop: 'ownerName', label: '签到人' }, { prop: 'planTime', label: '签到时间' }, { prop: 'status', label: '签到状态', type: 'select' }, { prop: 'remark', label: '签到说明', type: 'textarea' }]
</script>

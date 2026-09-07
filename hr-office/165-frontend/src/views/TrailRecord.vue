<template><DataPage title="轨迹留痕" description="轨迹编号、访问区域、轨迹类型、记录人员、记录时间和轨迹状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'SECURITY', 'EMPLOYEE', 'MANAGER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addTrailRecord, deleteTrailRecord, finishTrailRecord, getTrailRecordPage, processTrailRecord, updateTrailRecord } from '../api'
const api = { page: getTrailRecordPage, add: addTrailRecord, update: updateTrailRecord, delete: deleteTrailRecord, process: processTrailRecord, finish: finishTrailRecord }
const columns = [{ prop: 'recordNo', label: '轨迹编号' }, { prop: 'recordName', label: '访问区域' }, { prop: 'category', label: '轨迹类型' }, { prop: 'ownerName', label: '记录人员' }, { prop: 'planTime', label: '记录时间' }, { prop: 'status', label: '轨迹状态' }, { prop: 'remark', label: '轨迹说明' }]
const formFields = [{ prop: 'recordNo', label: '轨迹编号' }, { prop: 'recordName', label: '访问区域' }, { prop: 'category', label: '轨迹类型' }, { prop: 'ownerName', label: '记录人员' }, { prop: 'planTime', label: '记录时间' }, { prop: 'status', label: '轨迹状态', type: 'select' }, { prop: 'remark', label: '轨迹说明', type: 'textarea' }]
</script>

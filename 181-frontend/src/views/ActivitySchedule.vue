<template><DataPage title="托管活动" description="活动编号、活动名称、活动类型、负责老师、活动时间和活动状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CENTER', 'TEACHER', 'PARENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addActivitySchedule, deleteActivitySchedule, finishActivitySchedule, getActivitySchedulePage, processActivitySchedule, updateActivitySchedule } from '../api'
const api = { page: getActivitySchedulePage, add: addActivitySchedule, update: updateActivitySchedule, delete: deleteActivitySchedule, process: processActivitySchedule, finish: finishActivitySchedule }
const columns = [{ prop: 'recordNo', label: '活动编号' }, { prop: 'recordName', label: '活动名称' }, { prop: 'category', label: '活动类型' }, { prop: 'ownerName', label: '负责老师' }, { prop: 'planTime', label: '活动时间' }, { prop: 'status', label: '活动状态' }, { prop: 'remark', label: '活动说明' }]
const formFields = [{ prop: 'recordNo', label: '活动编号' }, { prop: 'recordName', label: '活动名称' }, { prop: 'category', label: '活动类型' }, { prop: 'ownerName', label: '负责老师' }, { prop: 'planTime', label: '活动时间' }, { prop: 'status', label: '活动状态', type: 'select' }, { prop: 'remark', label: '活动说明', type: 'textarea' }]
</script>

<template><DataPage title="讲解排期" description="排期编号、讲解路线、场次类型、讲解员、场次时间和排期状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'MANAGER', 'GUIDE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addGuideSchedule, deleteGuideSchedule, finishGuideSchedule, getGuideSchedulePage, processGuideSchedule, updateGuideSchedule } from '../api'
const api = { page: getGuideSchedulePage, add: addGuideSchedule, update: updateGuideSchedule, delete: deleteGuideSchedule, process: processGuideSchedule, finish: finishGuideSchedule }
const columns = [{ prop: 'recordNo', label: '排期编号' }, { prop: 'recordName', label: '讲解路线' }, { prop: 'category', label: '场次类型' }, { prop: 'ownerName', label: '讲解员' }, { prop: 'planTime', label: '场次时间' }, { prop: 'status', label: '排期状态' }, { prop: 'remark', label: '排期说明' }]
const formFields = [{ prop: 'recordNo', label: '排期编号' }, { prop: 'recordName', label: '讲解路线' }, { prop: 'category', label: '场次类型' }, { prop: 'ownerName', label: '讲解员' }, { prop: 'planTime', label: '场次时间' }, { prop: 'status', label: '排期状态', type: 'select' }, { prop: 'remark', label: '排期说明', type: 'textarea' }]
</script>

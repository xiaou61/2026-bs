<template><DataPage title="赛事报名" description="报名编号、报名项目、参赛队伍、报名负责人、提交时间和报名状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'ORGANIZER', 'COACH', 'ATHLETE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addEventRegistration, deleteEventRegistration, finishEventRegistration, getEventRegistrationPage, processEventRegistration, updateEventRegistration } from '../api'
const api = { page: getEventRegistrationPage, add: addEventRegistration, update: updateEventRegistration, delete: deleteEventRegistration, process: processEventRegistration, finish: finishEventRegistration }
const columns = [{ prop: 'recordNo', label: '报名编号' }, { prop: 'recordName', label: '报名项目' }, { prop: 'category', label: '参赛队伍' }, { prop: 'ownerName', label: '报名负责人' }, { prop: 'planTime', label: '提交时间' }, { prop: 'status', label: '报名状态' }, { prop: 'remark', label: '报名说明' }]
const formFields = [{ prop: 'recordNo', label: '报名编号' }, { prop: 'recordName', label: '报名项目' }, { prop: 'category', label: '参赛队伍' }, { prop: 'ownerName', label: '报名负责人' }, { prop: 'planTime', label: '提交时间' }, { prop: 'status', label: '报名状态', type: 'select' }, { prop: 'remark', label: '报名说明', type: 'textarea' }]
</script>

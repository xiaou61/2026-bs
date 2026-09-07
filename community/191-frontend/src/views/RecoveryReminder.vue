<template><DataPage title="回收提醒" description="提醒编号、借用器具、提醒类型、跟进人员、提醒时间和回收状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'COMMUNITY', 'AIDSTAFF', 'VOLUNTEER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addRecoveryReminder, deleteRecoveryReminder, finishRecoveryReminder, getRecoveryReminderPage, processRecoveryReminder, updateRecoveryReminder } from '../api'
const api = { page: getRecoveryReminderPage, add: addRecoveryReminder, update: updateRecoveryReminder, delete: deleteRecoveryReminder, process: processRecoveryReminder, finish: finishRecoveryReminder }
const columns = [{ prop: 'recordNo', label: '提醒编号' }, { prop: 'recordName', label: '借用器具' }, { prop: 'category', label: '提醒类型' }, { prop: 'ownerName', label: '跟进人员' }, { prop: 'planTime', label: '提醒时间' }, { prop: 'status', label: '回收状态' }, { prop: 'remark', label: '提醒说明' }]
const formFields = [{ prop: 'recordNo', label: '提醒编号' }, { prop: 'recordName', label: '借用器具' }, { prop: 'category', label: '提醒类型' }, { prop: 'ownerName', label: '跟进人员' }, { prop: 'planTime', label: '提醒时间' }, { prop: 'status', label: '回收状态', type: 'select' }, { prop: 'remark', label: '提醒说明', type: 'textarea' }]
</script>

<template><DataPage title="续训提醒" description="提醒编号、证书项目、提醒类型、提醒人员、提醒时间和续训状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'EDU', 'HR', 'NURSE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addRetrainingReminder, deleteRetrainingReminder, finishRetrainingReminder, getRetrainingReminderPage, processRetrainingReminder, updateRetrainingReminder } from '../api'
const api = { page: getRetrainingReminderPage, add: addRetrainingReminder, update: updateRetrainingReminder, delete: deleteRetrainingReminder, process: processRetrainingReminder, finish: finishRetrainingReminder }
const columns = [{ prop: 'recordNo', label: '提醒编号' }, { prop: 'recordName', label: '证书项目' }, { prop: 'category', label: '提醒类型' }, { prop: 'ownerName', label: '提醒人员' }, { prop: 'planTime', label: '提醒时间' }, { prop: 'status', label: '续训状态' }, { prop: 'remark', label: '提醒说明' }]
const formFields = [{ prop: 'recordNo', label: '提醒编号' }, { prop: 'recordName', label: '证书项目' }, { prop: 'category', label: '提醒类型' }, { prop: 'ownerName', label: '提醒人员' }, { prop: 'planTime', label: '提醒时间' }, { prop: 'status', label: '续训状态', type: 'select' }, { prop: 'remark', label: '提醒说明', type: 'textarea' }]
</script>

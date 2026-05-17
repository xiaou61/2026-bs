<template><DataPage title="续方提醒" description="提醒编号、慢病方案、提醒类型、随访人员、提醒时间和提醒状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'PHARMACY', 'FOLLOWUP', 'CUSTOMER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addRenewalReminder, deleteRenewalReminder, finishRenewalReminder, getRenewalReminderPage, processRenewalReminder, updateRenewalReminder } from '../api'
const api = { page: getRenewalReminderPage, add: addRenewalReminder, update: updateRenewalReminder, delete: deleteRenewalReminder, process: processRenewalReminder, finish: finishRenewalReminder }
const columns = [{ prop: 'recordNo', label: '提醒编号' }, { prop: 'recordName', label: '慢病方案' }, { prop: 'category', label: '提醒类型' }, { prop: 'ownerName', label: '随访人员' }, { prop: 'planTime', label: '提醒时间' }, { prop: 'status', label: '提醒状态' }, { prop: 'remark', label: '提醒说明' }]
const formFields = [{ prop: 'recordNo', label: '提醒编号' }, { prop: 'recordName', label: '慢病方案' }, { prop: 'category', label: '提醒类型' }, { prop: 'ownerName', label: '随访人员' }, { prop: 'planTime', label: '提醒时间' }, { prop: 'status', label: '提醒状态', type: 'select' }, { prop: 'remark', label: '提醒说明', type: 'textarea' }]
</script>

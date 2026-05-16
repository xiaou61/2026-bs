<template><DataPage title="续费提醒" description="提醒编号、月租合同、提醒类型、客服人员、提醒时间和提醒状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'PARKING', 'FINANCE', 'TENANT', 'SERVICE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addRenewalReminder, deleteRenewalReminder, finishRenewalReminder, getRenewalReminderPage, processRenewalReminder, updateRenewalReminder } from '../api'
const api = { page: getRenewalReminderPage, add: addRenewalReminder, update: updateRenewalReminder, delete: deleteRenewalReminder, process: processRenewalReminder, finish: finishRenewalReminder }
const columns = [{ prop: 'recordNo', label: '提醒编号' }, { prop: 'recordName', label: '月租合同' }, { prop: 'category', label: '提醒类型' }, { prop: 'ownerName', label: '客服人员' }, { prop: 'planTime', label: '提醒时间' }, { prop: 'status', label: '提醒状态' }, { prop: 'remark', label: '提醒说明' }]
const formFields = [{ prop: 'recordNo', label: '提醒编号' }, { prop: 'recordName', label: '月租合同' }, { prop: 'category', label: '提醒类型' }, { prop: 'ownerName', label: '客服人员' }, { prop: 'planTime', label: '提醒时间' }, { prop: 'status', label: '提醒状态', type: 'select' }, { prop: 'remark', label: '提醒说明', type: 'textarea' }]
</script>

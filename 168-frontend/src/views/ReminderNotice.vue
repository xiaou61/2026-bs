<template><DataPage title="提醒通知" description="提醒编号、提醒对象、提醒类型、发送人员、发送时间和提醒状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'TRAINING', 'CERTIFIER', 'TRAINEE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addReminderNotice, deleteReminderNotice, finishReminderNotice, getReminderNoticePage, processReminderNotice, updateReminderNotice } from '../api'
const api = { page: getReminderNoticePage, add: addReminderNotice, update: updateReminderNotice, delete: deleteReminderNotice, process: processReminderNotice, finish: finishReminderNotice }
const columns = [{ prop: 'recordNo', label: '提醒编号' }, { prop: 'recordName', label: '提醒对象' }, { prop: 'category', label: '提醒类型' }, { prop: 'ownerName', label: '发送人员' }, { prop: 'planTime', label: '发送时间' }, { prop: 'status', label: '提醒状态' }, { prop: 'remark', label: '提醒说明' }]
const formFields = [{ prop: 'recordNo', label: '提醒编号' }, { prop: 'recordName', label: '提醒对象' }, { prop: 'category', label: '提醒类型' }, { prop: 'ownerName', label: '发送人员' }, { prop: 'planTime', label: '发送时间' }, { prop: 'status', label: '提醒状态', type: 'select' }, { prop: 'remark', label: '提醒说明', type: 'textarea' }]
</script>

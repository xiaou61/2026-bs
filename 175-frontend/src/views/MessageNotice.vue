<template><DataPage title="消息通知" description="通知编号、通知标题、通知类型、发送人员、发送时间和通知状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'LIBRARY', 'CURATOR', 'STUDENT', 'CLUB', 'TEACHER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addMessageNotice, deleteMessageNotice, finishMessageNotice, getMessageNoticePage, processMessageNotice, updateMessageNotice } from '../api'
const api = { page: getMessageNoticePage, add: addMessageNotice, update: updateMessageNotice, delete: deleteMessageNotice, process: processMessageNotice, finish: finishMessageNotice }
const columns = [{ prop: 'recordNo', label: '通知编号' }, { prop: 'recordName', label: '通知标题' }, { prop: 'category', label: '通知类型' }, { prop: 'ownerName', label: '发送人员' }, { prop: 'planTime', label: '发送时间' }, { prop: 'status', label: '通知状态' }, { prop: 'remark', label: '通知说明' }]
const formFields = [{ prop: 'recordNo', label: '通知编号' }, { prop: 'recordName', label: '通知标题' }, { prop: 'category', label: '通知类型' }, { prop: 'ownerName', label: '发送人员' }, { prop: 'planTime', label: '发送时间' }, { prop: 'status', label: '通知状态', type: 'select' }, { prop: 'remark', label: '通知说明', type: 'textarea' }]
</script>

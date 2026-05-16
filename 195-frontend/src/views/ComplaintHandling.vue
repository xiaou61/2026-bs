<template><DataPage title="投诉处理" description="投诉编号、办理事项、投诉类型、处理人员、投诉时间和处理状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CENTER', 'SUPERVISE', 'CITIZEN']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addComplaintHandling, deleteComplaintHandling, finishComplaintHandling, getComplaintHandlingPage, processComplaintHandling, updateComplaintHandling } from '../api'
const api = { page: getComplaintHandlingPage, add: addComplaintHandling, update: updateComplaintHandling, delete: deleteComplaintHandling, process: processComplaintHandling, finish: finishComplaintHandling }
const columns = [{ prop: 'recordNo', label: '投诉编号' }, { prop: 'recordName', label: '办理事项' }, { prop: 'category', label: '投诉类型' }, { prop: 'ownerName', label: '处理人员' }, { prop: 'planTime', label: '投诉时间' }, { prop: 'status', label: '处理状态' }, { prop: 'remark', label: '投诉说明' }]
const formFields = [{ prop: 'recordNo', label: '投诉编号' }, { prop: 'recordName', label: '办理事项' }, { prop: 'category', label: '投诉类型' }, { prop: 'ownerName', label: '处理人员' }, { prop: 'planTime', label: '投诉时间' }, { prop: 'status', label: '处理状态', type: 'select' }, { prop: 'remark', label: '投诉说明', type: 'textarea' }]
</script>

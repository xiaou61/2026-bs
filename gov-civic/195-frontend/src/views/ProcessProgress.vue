<template><DataPage title="办理进度" description="进度编号、预约事项、办理环节、经办人员、办理时间和办理状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CENTER', 'WINDOW', 'REVIEW', 'CITIZEN']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addProcessProgress, deleteProcessProgress, finishProcessProgress, getProcessProgressPage, processProcessProgress, updateProcessProgress } from '../api'
const api = { page: getProcessProgressPage, add: addProcessProgress, update: updateProcessProgress, delete: deleteProcessProgress, process: processProcessProgress, finish: finishProcessProgress }
const columns = [{ prop: 'recordNo', label: '进度编号' }, { prop: 'recordName', label: '预约事项' }, { prop: 'category', label: '办理环节' }, { prop: 'ownerName', label: '经办人员' }, { prop: 'planTime', label: '办理时间' }, { prop: 'status', label: '办理状态' }, { prop: 'remark', label: '办理说明' }]
const formFields = [{ prop: 'recordNo', label: '进度编号' }, { prop: 'recordName', label: '预约事项' }, { prop: 'category', label: '办理环节' }, { prop: 'ownerName', label: '经办人员' }, { prop: 'planTime', label: '办理时间' }, { prop: 'status', label: '办理状态', type: 'select' }, { prop: 'remark', label: '办理说明', type: 'textarea' }]
</script>

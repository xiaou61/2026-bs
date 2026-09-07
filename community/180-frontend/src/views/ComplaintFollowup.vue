<template><DataPage title="投诉回访" description="回访编号、投诉事项、回访类型、客服人员、回访时间和回访状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'PROPERTY', 'FINANCE', 'OWNER', 'WORKER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addComplaintFollowup, deleteComplaintFollowup, finishComplaintFollowup, getComplaintFollowupPage, processComplaintFollowup, updateComplaintFollowup } from '../api'
const api = { page: getComplaintFollowupPage, add: addComplaintFollowup, update: updateComplaintFollowup, delete: deleteComplaintFollowup, process: processComplaintFollowup, finish: finishComplaintFollowup }
const columns = [{ prop: 'recordNo', label: '回访编号' }, { prop: 'recordName', label: '投诉事项' }, { prop: 'category', label: '回访类型' }, { prop: 'ownerName', label: '客服人员' }, { prop: 'planTime', label: '回访时间' }, { prop: 'status', label: '回访状态' }, { prop: 'remark', label: '回访说明' }]
const formFields = [{ prop: 'recordNo', label: '回访编号' }, { prop: 'recordName', label: '投诉事项' }, { prop: 'category', label: '回访类型' }, { prop: 'ownerName', label: '客服人员' }, { prop: 'planTime', label: '回访时间' }, { prop: 'status', label: '回访状态', type: 'select' }, { prop: 'remark', label: '回访说明', type: 'textarea' }]
</script>

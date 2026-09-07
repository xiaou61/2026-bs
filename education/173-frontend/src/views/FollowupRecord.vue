<template><DataPage title="跟踪回访" description="回访编号、回访学生、回访类型、回访人员、回访时间和回访状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CAREER', 'COLLEGE', 'COUNSELOR', 'STUDENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addFollowupRecord, deleteFollowupRecord, finishFollowupRecord, getFollowupRecordPage, processFollowupRecord, updateFollowupRecord } from '../api'
const api = { page: getFollowupRecordPage, add: addFollowupRecord, update: updateFollowupRecord, delete: deleteFollowupRecord, process: processFollowupRecord, finish: finishFollowupRecord }
const columns = [{ prop: 'recordNo', label: '回访编号' }, { prop: 'recordName', label: '回访学生' }, { prop: 'category', label: '回访类型' }, { prop: 'ownerName', label: '回访人员' }, { prop: 'planTime', label: '回访时间' }, { prop: 'status', label: '回访状态' }, { prop: 'remark', label: '回访说明' }]
const formFields = [{ prop: 'recordNo', label: '回访编号' }, { prop: 'recordName', label: '回访学生' }, { prop: 'category', label: '回访类型' }, { prop: 'ownerName', label: '回访人员' }, { prop: 'planTime', label: '回访时间' }, { prop: 'status', label: '回访状态', type: 'select' }, { prop: 'remark', label: '回访说明', type: 'textarea' }]
</script>

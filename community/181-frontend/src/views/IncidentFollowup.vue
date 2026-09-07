<template><DataPage title="事件跟进" description="跟进编号、安全事件、跟进类型、跟进人员、跟进时间和跟进状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CENTER', 'SECURITY', 'TEACHER', 'PARENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addIncidentFollowup, deleteIncidentFollowup, finishIncidentFollowup, getIncidentFollowupPage, processIncidentFollowup, updateIncidentFollowup } from '../api'
const api = { page: getIncidentFollowupPage, add: addIncidentFollowup, update: updateIncidentFollowup, delete: deleteIncidentFollowup, process: processIncidentFollowup, finish: finishIncidentFollowup }
const columns = [{ prop: 'recordNo', label: '跟进编号' }, { prop: 'recordName', label: '安全事件' }, { prop: 'category', label: '跟进类型' }, { prop: 'ownerName', label: '跟进人员' }, { prop: 'planTime', label: '跟进时间' }, { prop: 'status', label: '跟进状态' }, { prop: 'remark', label: '跟进说明' }]
const formFields = [{ prop: 'recordNo', label: '跟进编号' }, { prop: 'recordName', label: '安全事件' }, { prop: 'category', label: '跟进类型' }, { prop: 'ownerName', label: '跟进人员' }, { prop: 'planTime', label: '跟进时间' }, { prop: 'status', label: '跟进状态', type: 'select' }, { prop: 'remark', label: '跟进说明', type: 'textarea' }]
</script>

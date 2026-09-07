<template><DataPage title="过程跟踪" description="跟踪编号、培养计划、跟踪类型、跟进人员、跟踪时间和跟踪状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'ACADEMIC', 'MENTOR', 'COUNSELOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addProcessTracking, deleteProcessTracking, finishProcessTracking, getProcessTrackingPage, processProcessTracking, updateProcessTracking } from '../api'
const api = { page: getProcessTrackingPage, add: addProcessTracking, update: updateProcessTracking, delete: deleteProcessTracking, process: processProcessTracking, finish: finishProcessTracking }
const columns = [{ prop: 'recordNo', label: '跟踪编号' }, { prop: 'recordName', label: '培养计划' }, { prop: 'category', label: '跟踪类型' }, { prop: 'ownerName', label: '跟进人员' }, { prop: 'planTime', label: '跟踪时间' }, { prop: 'status', label: '跟踪状态' }, { prop: 'remark', label: '跟踪说明' }]
const formFields = [{ prop: 'recordNo', label: '跟踪编号' }, { prop: 'recordName', label: '培养计划' }, { prop: 'category', label: '跟踪类型' }, { prop: 'ownerName', label: '跟进人员' }, { prop: 'planTime', label: '跟踪时间' }, { prop: 'status', label: '跟踪状态', type: 'select' }, { prop: 'remark', label: '跟踪说明', type: 'textarea' }]
</script>

<template><DataPage title="维修跟踪" description="跟踪编号、维修工单、跟踪类型、维修人员、跟踪时间和跟踪状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'STATION', 'MECHANIC', 'DRIVER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addRepairFollowup, deleteRepairFollowup, finishRepairFollowup, getRepairFollowupPage, processRepairFollowup, updateRepairFollowup } from '../api'
const api = { page: getRepairFollowupPage, add: addRepairFollowup, update: updateRepairFollowup, delete: deleteRepairFollowup, process: processRepairFollowup, finish: finishRepairFollowup }
const columns = [{ prop: 'recordNo', label: '跟踪编号' }, { prop: 'recordName', label: '维修工单' }, { prop: 'category', label: '跟踪类型' }, { prop: 'ownerName', label: '维修人员' }, { prop: 'planTime', label: '跟踪时间' }, { prop: 'status', label: '跟踪状态' }, { prop: 'remark', label: '跟踪说明' }]
const formFields = [{ prop: 'recordNo', label: '跟踪编号' }, { prop: 'recordName', label: '维修工单' }, { prop: 'category', label: '跟踪类型' }, { prop: 'ownerName', label: '维修人员' }, { prop: 'planTime', label: '跟踪时间' }, { prop: 'status', label: '跟踪状态', type: 'select' }, { prop: 'remark', label: '跟踪说明', type: 'textarea' }]
</script>

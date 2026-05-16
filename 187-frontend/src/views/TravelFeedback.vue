<template><DataPage title="出行反馈" description="反馈编号、关联行程、反馈类型、反馈人员、反馈时间和反馈状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'AGENCY', 'LEADER', 'PARTICIPANT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addTravelFeedback, deleteTravelFeedback, finishTravelFeedback, getTravelFeedbackPage, processTravelFeedback, updateTravelFeedback } from '../api'
const api = { page: getTravelFeedbackPage, add: addTravelFeedback, update: updateTravelFeedback, delete: deleteTravelFeedback, process: processTravelFeedback, finish: finishTravelFeedback }
const columns = [{ prop: 'recordNo', label: '反馈编号' }, { prop: 'recordName', label: '关联行程' }, { prop: 'category', label: '反馈类型' }, { prop: 'ownerName', label: '反馈人员' }, { prop: 'planTime', label: '反馈时间' }, { prop: 'status', label: '反馈状态' }, { prop: 'remark', label: '反馈说明' }]
const formFields = [{ prop: 'recordNo', label: '反馈编号' }, { prop: 'recordName', label: '关联行程' }, { prop: 'category', label: '反馈类型' }, { prop: 'ownerName', label: '反馈人员' }, { prop: 'planTime', label: '反馈时间' }, { prop: 'status', label: '反馈状态', type: 'select' }, { prop: 'remark', label: '反馈说明', type: 'textarea' }]
</script>

<template><DataPage title="康复反馈" description="反馈编号、训练安排、反馈类型、康复治疗师、反馈时间和反馈状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CENTER', 'THERAPIST', 'COACH', 'MEMBER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addRehabFeedback, deleteRehabFeedback, finishRehabFeedback, getRehabFeedbackPage, processRehabFeedback, updateRehabFeedback } from '../api'
const api = { page: getRehabFeedbackPage, add: addRehabFeedback, update: updateRehabFeedback, delete: deleteRehabFeedback, process: processRehabFeedback, finish: finishRehabFeedback }
const columns = [{ prop: 'recordNo', label: '反馈编号' }, { prop: 'recordName', label: '训练安排' }, { prop: 'category', label: '反馈类型' }, { prop: 'ownerName', label: '康复治疗师' }, { prop: 'planTime', label: '反馈时间' }, { prop: 'status', label: '反馈状态' }, { prop: 'remark', label: '反馈说明' }]
const formFields = [{ prop: 'recordNo', label: '反馈编号' }, { prop: 'recordName', label: '训练安排' }, { prop: 'category', label: '反馈类型' }, { prop: 'ownerName', label: '康复治疗师' }, { prop: 'planTime', label: '反馈时间' }, { prop: 'status', label: '反馈状态', type: 'select' }, { prop: 'remark', label: '反馈说明', type: 'textarea' }]
</script>

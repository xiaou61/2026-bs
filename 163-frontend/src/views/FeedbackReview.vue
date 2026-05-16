<template><DataPage title="反馈整改" description="反馈编号、反馈对象、反馈类型、负责人、反馈时间和整改状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'SECRETARY', 'DIRECTOR', 'TEACHER', 'STUDENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addFeedbackReview, deleteFeedbackReview, finishFeedbackReview, getFeedbackReviewPage, processFeedbackReview, updateFeedbackReview } from '../api'
const api = { page: getFeedbackReviewPage, add: addFeedbackReview, update: updateFeedbackReview, delete: deleteFeedbackReview, process: processFeedbackReview, finish: finishFeedbackReview }
const columns = [{ prop: 'recordNo', label: '反馈编号' }, { prop: 'recordName', label: '反馈对象' }, { prop: 'category', label: '反馈类型' }, { prop: 'ownerName', label: '负责人' }, { prop: 'planTime', label: '反馈时间' }, { prop: 'status', label: '整改状态' }, { prop: 'remark', label: '反馈说明' }]
const formFields = [{ prop: 'recordNo', label: '反馈编号' }, { prop: 'recordName', label: '反馈对象' }, { prop: 'category', label: '反馈类型' }, { prop: 'ownerName', label: '负责人' }, { prop: 'planTime', label: '反馈时间' }, { prop: 'status', label: '整改状态', type: 'select' }, { prop: 'remark', label: '反馈说明', type: 'textarea' }]
</script>

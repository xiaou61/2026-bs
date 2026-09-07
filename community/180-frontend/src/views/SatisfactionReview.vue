<template><DataPage title="满意评价" description="评价编号、服务工单、评价类型、评价业主、评价时间和评价状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'PROPERTY', 'OWNER', 'WORKER', 'FINANCE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addSatisfactionReview, deleteSatisfactionReview, finishSatisfactionReview, getSatisfactionReviewPage, processSatisfactionReview, updateSatisfactionReview } from '../api'
const api = { page: getSatisfactionReviewPage, add: addSatisfactionReview, update: updateSatisfactionReview, delete: deleteSatisfactionReview, process: processSatisfactionReview, finish: finishSatisfactionReview }
const columns = [{ prop: 'recordNo', label: '评价编号' }, { prop: 'recordName', label: '服务工单' }, { prop: 'category', label: '评价类型' }, { prop: 'ownerName', label: '评价业主' }, { prop: 'planTime', label: '评价时间' }, { prop: 'status', label: '评价状态' }, { prop: 'remark', label: '评价说明' }]
const formFields = [{ prop: 'recordNo', label: '评价编号' }, { prop: 'recordName', label: '服务工单' }, { prop: 'category', label: '评价类型' }, { prop: 'ownerName', label: '评价业主' }, { prop: 'planTime', label: '评价时间' }, { prop: 'status', label: '评价状态', type: 'select' }, { prop: 'remark', label: '评价说明', type: 'textarea' }]
</script>

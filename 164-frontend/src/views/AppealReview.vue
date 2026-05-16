<template><DataPage title="申诉复核" description="申诉编号、申诉赛事、申诉类型、复核委员、申诉时间和复核状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'ORGANIZER', 'COACH', 'ATHLETE', 'COMMITTEE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addAppealReview, deleteAppealReview, finishAppealReview, getAppealReviewPage, processAppealReview, updateAppealReview } from '../api'
const api = { page: getAppealReviewPage, add: addAppealReview, update: updateAppealReview, delete: deleteAppealReview, process: processAppealReview, finish: finishAppealReview }
const columns = [{ prop: 'recordNo', label: '申诉编号' }, { prop: 'recordName', label: '申诉赛事' }, { prop: 'category', label: '申诉类型' }, { prop: 'ownerName', label: '复核委员' }, { prop: 'planTime', label: '申诉时间' }, { prop: 'status', label: '复核状态' }, { prop: 'remark', label: '申诉说明' }]
const formFields = [{ prop: 'recordNo', label: '申诉编号' }, { prop: 'recordName', label: '申诉赛事' }, { prop: 'category', label: '申诉类型' }, { prop: 'ownerName', label: '复核委员' }, { prop: 'planTime', label: '申诉时间' }, { prop: 'status', label: '复核状态', type: 'select' }, { prop: 'remark', label: '申诉说明', type: 'textarea' }]
</script>

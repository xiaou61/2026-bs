<template><DataPage title="资格评审" description="评审编号、报名申请、评审类型、评审人员、评审时间和评审状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'ACADEMIC', 'REVIEWER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addSelectionReview, deleteSelectionReview, finishSelectionReview, getSelectionReviewPage, processSelectionReview, updateSelectionReview } from '../api'
const api = { page: getSelectionReviewPage, add: addSelectionReview, update: updateSelectionReview, delete: deleteSelectionReview, process: processSelectionReview, finish: finishSelectionReview }
const columns = [{ prop: 'recordNo', label: '评审编号' }, { prop: 'recordName', label: '报名申请' }, { prop: 'category', label: '评审类型' }, { prop: 'ownerName', label: '评审人员' }, { prop: 'planTime', label: '评审时间' }, { prop: 'status', label: '评审状态' }, { prop: 'remark', label: '评审说明' }]
const formFields = [{ prop: 'recordNo', label: '评审编号' }, { prop: 'recordName', label: '报名申请' }, { prop: 'category', label: '评审类型' }, { prop: 'ownerName', label: '评审人员' }, { prop: 'planTime', label: '评审时间' }, { prop: 'status', label: '评审状态', type: 'select' }, { prop: 'remark', label: '评审说明', type: 'textarea' }]
</script>

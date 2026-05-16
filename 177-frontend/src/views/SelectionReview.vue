<template><DataPage title="选品评测" description="评测编号、评测商品、评测类型、选品人员、评测时间和评测状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'BASE', 'SELECTOR', 'ANCHOR', 'MERCHANT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addSelectionReview, deleteSelectionReview, finishSelectionReview, getSelectionReviewPage, processSelectionReview, updateSelectionReview } from '../api'
const api = { page: getSelectionReviewPage, add: addSelectionReview, update: updateSelectionReview, delete: deleteSelectionReview, process: processSelectionReview, finish: finishSelectionReview }
const columns = [{ prop: 'recordNo', label: '评测编号' }, { prop: 'recordName', label: '评测商品' }, { prop: 'category', label: '评测类型' }, { prop: 'ownerName', label: '选品人员' }, { prop: 'planTime', label: '评测时间' }, { prop: 'status', label: '评测状态' }, { prop: 'remark', label: '评测说明' }]
const formFields = [{ prop: 'recordNo', label: '评测编号' }, { prop: 'recordName', label: '评测商品' }, { prop: 'category', label: '评测类型' }, { prop: 'ownerName', label: '选品人员' }, { prop: 'planTime', label: '评测时间' }, { prop: 'status', label: '评测状态', type: 'select' }, { prop: 'remark', label: '评测说明', type: 'textarea' }]
</script>

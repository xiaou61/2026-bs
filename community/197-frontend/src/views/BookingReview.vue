<template><DataPage title="预约审核" description="审核编号、服务预约、审核类型、审核人员、审核时间和审核状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'AGENCY', 'DISPATCH']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addBookingReview, deleteBookingReview, finishBookingReview, getBookingReviewPage, processBookingReview, updateBookingReview } from '../api'
const api = { page: getBookingReviewPage, add: addBookingReview, update: updateBookingReview, delete: deleteBookingReview, process: processBookingReview, finish: finishBookingReview }
const columns = [{ prop: 'recordNo', label: '审核编号' }, { prop: 'recordName', label: '服务预约' }, { prop: 'category', label: '审核类型' }, { prop: 'ownerName', label: '审核人员' }, { prop: 'planTime', label: '审核时间' }, { prop: 'status', label: '审核状态' }, { prop: 'remark', label: '审核说明' }]
const formFields = [{ prop: 'recordNo', label: '审核编号' }, { prop: 'recordName', label: '服务预约' }, { prop: 'category', label: '审核类型' }, { prop: 'ownerName', label: '审核人员' }, { prop: 'planTime', label: '审核时间' }, { prop: 'status', label: '审核状态', type: 'select' }, { prop: 'remark', label: '审核说明', type: 'textarea' }]
</script>

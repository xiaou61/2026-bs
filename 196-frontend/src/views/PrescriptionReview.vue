<template><DataPage title="处方审核" description="审核编号、处方记录、审核类型、审核药师、审核时间和审核状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'PHARMACY', 'PHARMACIST']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addPrescriptionReview, deletePrescriptionReview, finishPrescriptionReview, getPrescriptionReviewPage, processPrescriptionReview, updatePrescriptionReview } from '../api'
const api = { page: getPrescriptionReviewPage, add: addPrescriptionReview, update: updatePrescriptionReview, delete: deletePrescriptionReview, process: processPrescriptionReview, finish: finishPrescriptionReview }
const columns = [{ prop: 'recordNo', label: '审核编号' }, { prop: 'recordName', label: '处方记录' }, { prop: 'category', label: '审核类型' }, { prop: 'ownerName', label: '审核药师' }, { prop: 'planTime', label: '审核时间' }, { prop: 'status', label: '审核状态' }, { prop: 'remark', label: '审核说明' }]
const formFields = [{ prop: 'recordNo', label: '审核编号' }, { prop: 'recordName', label: '处方记录' }, { prop: 'category', label: '审核类型' }, { prop: 'ownerName', label: '审核药师' }, { prop: 'planTime', label: '审核时间' }, { prop: 'status', label: '审核状态', type: 'select' }, { prop: 'remark', label: '审核说明', type: 'textarea' }]
</script>

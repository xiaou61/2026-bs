<template><DataPage title="材料审核" description="审核编号、审核学生、材料类型、审核人员、审核时间和审核状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CAREER', 'COLLEGE', 'COUNSELOR', 'STUDENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addMaterialReview, deleteMaterialReview, finishMaterialReview, getMaterialReviewPage, processMaterialReview, updateMaterialReview } from '../api'
const api = { page: getMaterialReviewPage, add: addMaterialReview, update: updateMaterialReview, delete: deleteMaterialReview, process: processMaterialReview, finish: finishMaterialReview }
const columns = [{ prop: 'recordNo', label: '审核编号' }, { prop: 'recordName', label: '审核学生' }, { prop: 'category', label: '材料类型' }, { prop: 'ownerName', label: '审核人员' }, { prop: 'planTime', label: '审核时间' }, { prop: 'status', label: '审核状态' }, { prop: 'remark', label: '审核说明' }]
const formFields = [{ prop: 'recordNo', label: '审核编号' }, { prop: 'recordName', label: '审核学生' }, { prop: 'category', label: '材料类型' }, { prop: 'ownerName', label: '审核人员' }, { prop: 'planTime', label: '审核时间' }, { prop: 'status', label: '审核状态', type: 'select' }, { prop: 'remark', label: '审核说明', type: 'textarea' }]
</script>

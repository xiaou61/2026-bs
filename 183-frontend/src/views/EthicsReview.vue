<template><DataPage title="伦理审批" description="审批编号、伦理申请、审批类型、审批人员、审批时间和审批状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'ETHICS', 'RESEARCHER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addEthicsReview, deleteEthicsReview, finishEthicsReview, getEthicsReviewPage, processEthicsReview, updateEthicsReview } from '../api'
const api = { page: getEthicsReviewPage, add: addEthicsReview, update: updateEthicsReview, delete: deleteEthicsReview, process: processEthicsReview, finish: finishEthicsReview }
const columns = [{ prop: 'recordNo', label: '审批编号' }, { prop: 'recordName', label: '伦理申请' }, { prop: 'category', label: '审批类型' }, { prop: 'ownerName', label: '审批人员' }, { prop: 'planTime', label: '审批时间' }, { prop: 'status', label: '审批状态' }, { prop: 'remark', label: '审批说明' }]
const formFields = [{ prop: 'recordNo', label: '审批编号' }, { prop: 'recordName', label: '伦理申请' }, { prop: 'category', label: '审批类型' }, { prop: 'ownerName', label: '审批人员' }, { prop: 'planTime', label: '审批时间' }, { prop: 'status', label: '审批状态', type: 'select' }, { prop: 'remark', label: '审批说明', type: 'textarea' }]
</script>

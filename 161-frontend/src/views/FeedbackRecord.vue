<template><DataPage title="回访评价" description="回访编号、回访游客、回访类型、回访人、回访时间和回访状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'SERVICE', 'VISITOR', 'BROADCAST']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addFeedbackRecord, deleteFeedbackRecord, finishFeedbackRecord, getFeedbackRecordPage, processFeedbackRecord, updateFeedbackRecord } from '../api'
const api = { page: getFeedbackRecordPage, add: addFeedbackRecord, update: updateFeedbackRecord, delete: deleteFeedbackRecord, process: processFeedbackRecord, finish: finishFeedbackRecord }
const columns = [{ prop: 'recordNo', label: '回访编号' }, { prop: 'recordName', label: '回访游客' }, { prop: 'category', label: '回访类型' }, { prop: 'ownerName', label: '回访人' }, { prop: 'planTime', label: '回访时间' }, { prop: 'status', label: '回访状态' }, { prop: 'remark', label: '回访说明' }]
const formFields = [{ prop: 'recordNo', label: '回访编号' }, { prop: 'recordName', label: '回访游客' }, { prop: 'category', label: '回访类型' }, { prop: 'ownerName', label: '回访人' }, { prop: 'planTime', label: '回访时间' }, { prop: 'status', label: '回访状态', type: 'select' }, { prop: 'remark', label: '回访说明', type: 'textarea' }]
</script>

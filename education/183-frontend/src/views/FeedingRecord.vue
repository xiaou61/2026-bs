<template><DataPage title="饲养记录" description="记录编号、实验动物、饲养类型、饲养人员、饲养时间和记录状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'LAB', 'BREEDER', 'VET']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addFeedingRecord, deleteFeedingRecord, finishFeedingRecord, getFeedingRecordPage, processFeedingRecord, updateFeedingRecord } from '../api'
const api = { page: getFeedingRecordPage, add: addFeedingRecord, update: updateFeedingRecord, delete: deleteFeedingRecord, process: processFeedingRecord, finish: finishFeedingRecord }
const columns = [{ prop: 'recordNo', label: '记录编号' }, { prop: 'recordName', label: '实验动物' }, { prop: 'category', label: '饲养类型' }, { prop: 'ownerName', label: '饲养人员' }, { prop: 'planTime', label: '饲养时间' }, { prop: 'status', label: '记录状态' }, { prop: 'remark', label: '记录说明' }]
const formFields = [{ prop: 'recordNo', label: '记录编号' }, { prop: 'recordName', label: '实验动物' }, { prop: 'category', label: '饲养类型' }, { prop: 'ownerName', label: '饲养人员' }, { prop: 'planTime', label: '饲养时间' }, { prop: 'status', label: '记录状态', type: 'select' }, { prop: 'remark', label: '记录说明', type: 'textarea' }]
</script>

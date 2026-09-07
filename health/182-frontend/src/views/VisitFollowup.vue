<template><DataPage title="回访关怀" description="回访编号、助餐老人、回访类型、回访人员、回访时间和回访状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CENTER', 'DIETITIAN', 'DISPATCH', 'ELDER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addVisitFollowup, deleteVisitFollowup, finishVisitFollowup, getVisitFollowupPage, processVisitFollowup, updateVisitFollowup } from '../api'
const api = { page: getVisitFollowupPage, add: addVisitFollowup, update: updateVisitFollowup, delete: deleteVisitFollowup, process: processVisitFollowup, finish: finishVisitFollowup }
const columns = [{ prop: 'recordNo', label: '回访编号' }, { prop: 'recordName', label: '助餐老人' }, { prop: 'category', label: '回访类型' }, { prop: 'ownerName', label: '回访人员' }, { prop: 'planTime', label: '回访时间' }, { prop: 'status', label: '回访状态' }, { prop: 'remark', label: '回访说明' }]
const formFields = [{ prop: 'recordNo', label: '回访编号' }, { prop: 'recordName', label: '助餐老人' }, { prop: 'category', label: '回访类型' }, { prop: 'ownerName', label: '回访人员' }, { prop: 'planTime', label: '回访时间' }, { prop: 'status', label: '回访状态', type: 'select' }, { prop: 'remark', label: '回访说明', type: 'textarea' }]
</script>

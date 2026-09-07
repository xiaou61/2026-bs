<template><DataPage title="复评记录" description="复评编号、会员姓名、复评类型、评估人员、复评时间和复评状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CENTER', 'ASSESSOR', 'THERAPIST', 'MEMBER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addReassessmentRecord, deleteReassessmentRecord, finishReassessmentRecord, getReassessmentRecordPage, processReassessmentRecord, updateReassessmentRecord } from '../api'
const api = { page: getReassessmentRecordPage, add: addReassessmentRecord, update: updateReassessmentRecord, delete: deleteReassessmentRecord, process: processReassessmentRecord, finish: finishReassessmentRecord }
const columns = [{ prop: 'recordNo', label: '复评编号' }, { prop: 'recordName', label: '会员姓名' }, { prop: 'category', label: '复评类型' }, { prop: 'ownerName', label: '评估人员' }, { prop: 'planTime', label: '复评时间' }, { prop: 'status', label: '复评状态' }, { prop: 'remark', label: '复评说明' }]
const formFields = [{ prop: 'recordNo', label: '复评编号' }, { prop: 'recordName', label: '会员姓名' }, { prop: 'category', label: '复评类型' }, { prop: 'ownerName', label: '评估人员' }, { prop: 'planTime', label: '复评时间' }, { prop: 'status', label: '复评状态', type: 'select' }, { prop: 'remark', label: '复评说明', type: 'textarea' }]
</script>

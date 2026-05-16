<template><DataPage title="岗位推荐" description="推荐编号、推荐岗位、推荐类型、推荐人员、推荐时间和推荐状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CAREER', 'COLLEGE', 'COUNSELOR', 'STUDENT', 'EMPLOYER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addJobRecommendation, deleteJobRecommendation, finishJobRecommendation, getJobRecommendationPage, processJobRecommendation, updateJobRecommendation } from '../api'
const api = { page: getJobRecommendationPage, add: addJobRecommendation, update: updateJobRecommendation, delete: deleteJobRecommendation, process: processJobRecommendation, finish: finishJobRecommendation }
const columns = [{ prop: 'recordNo', label: '推荐编号' }, { prop: 'recordName', label: '推荐岗位' }, { prop: 'category', label: '推荐类型' }, { prop: 'ownerName', label: '推荐人员' }, { prop: 'planTime', label: '推荐时间' }, { prop: 'status', label: '推荐状态' }, { prop: 'remark', label: '推荐说明' }]
const formFields = [{ prop: 'recordNo', label: '推荐编号' }, { prop: 'recordName', label: '推荐岗位' }, { prop: 'category', label: '推荐类型' }, { prop: 'ownerName', label: '推荐人员' }, { prop: 'planTime', label: '推荐时间' }, { prop: 'status', label: '推荐状态', type: 'select' }, { prop: 'remark', label: '推荐说明', type: 'textarea' }]
</script>

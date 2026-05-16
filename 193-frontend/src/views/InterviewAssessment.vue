<template><DataPage title="面试考核" description="考核编号、报名学生、考核项目、评委人员、考核时间和考核状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'ACADEMIC', 'REVIEWER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addInterviewAssessment, deleteInterviewAssessment, finishInterviewAssessment, getInterviewAssessmentPage, processInterviewAssessment, updateInterviewAssessment } from '../api'
const api = { page: getInterviewAssessmentPage, add: addInterviewAssessment, update: updateInterviewAssessment, delete: deleteInterviewAssessment, process: processInterviewAssessment, finish: finishInterviewAssessment }
const columns = [{ prop: 'recordNo', label: '考核编号' }, { prop: 'recordName', label: '报名学生' }, { prop: 'category', label: '考核项目' }, { prop: 'ownerName', label: '评委人员' }, { prop: 'planTime', label: '考核时间' }, { prop: 'status', label: '考核状态' }, { prop: 'remark', label: '考核说明' }]
const formFields = [{ prop: 'recordNo', label: '考核编号' }, { prop: 'recordName', label: '报名学生' }, { prop: 'category', label: '考核项目' }, { prop: 'ownerName', label: '评委人员' }, { prop: 'planTime', label: '考核时间' }, { prop: 'status', label: '考核状态', type: 'select' }, { prop: 'remark', label: '考核说明', type: 'textarea' }]
</script>

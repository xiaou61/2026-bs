<template><DataPage title="技能考核" description="考核编号、考核项目、考核类型、考核老师、考核时间和考核状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'EDU', 'ASSESSOR', 'NURSE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addSkillAssessment, deleteSkillAssessment, finishSkillAssessment, getSkillAssessmentPage, processSkillAssessment, updateSkillAssessment } from '../api'
const api = { page: getSkillAssessmentPage, add: addSkillAssessment, update: updateSkillAssessment, delete: deleteSkillAssessment, process: processSkillAssessment, finish: finishSkillAssessment }
const columns = [{ prop: 'recordNo', label: '考核编号' }, { prop: 'recordName', label: '考核项目' }, { prop: 'category', label: '考核类型' }, { prop: 'ownerName', label: '考核老师' }, { prop: 'planTime', label: '考核时间' }, { prop: 'status', label: '考核状态' }, { prop: 'remark', label: '考核说明' }]
const formFields = [{ prop: 'recordNo', label: '考核编号' }, { prop: 'recordName', label: '考核项目' }, { prop: 'category', label: '考核类型' }, { prop: 'ownerName', label: '考核老师' }, { prop: 'planTime', label: '考核时间' }, { prop: 'status', label: '考核状态', type: 'select' }, { prop: 'remark', label: '考核说明', type: 'textarea' }]
</script>

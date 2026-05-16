<template><DataPage title="考核申诉" description="申诉编号、关联考核、申诉类型、申诉人员、申诉时间和处理状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'EDU', 'ASSESSOR', 'NURSE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addAssessmentAppeal, deleteAssessmentAppeal, finishAssessmentAppeal, getAssessmentAppealPage, processAssessmentAppeal, updateAssessmentAppeal } from '../api'
const api = { page: getAssessmentAppealPage, add: addAssessmentAppeal, update: updateAssessmentAppeal, delete: deleteAssessmentAppeal, process: processAssessmentAppeal, finish: finishAssessmentAppeal }
const columns = [{ prop: 'recordNo', label: '申诉编号' }, { prop: 'recordName', label: '关联考核' }, { prop: 'category', label: '申诉类型' }, { prop: 'ownerName', label: '申诉人员' }, { prop: 'planTime', label: '申诉时间' }, { prop: 'status', label: '处理状态' }, { prop: 'remark', label: '申诉说明' }]
const formFields = [{ prop: 'recordNo', label: '申诉编号' }, { prop: 'recordName', label: '关联考核' }, { prop: 'category', label: '申诉类型' }, { prop: 'ownerName', label: '申诉人员' }, { prop: 'planTime', label: '申诉时间' }, { prop: 'status', label: '处理状态', type: 'select' }, { prop: 'remark', label: '申诉说明', type: 'textarea' }]
</script>

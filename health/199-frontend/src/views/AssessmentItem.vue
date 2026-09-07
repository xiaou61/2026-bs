<template><DataPage title="体测项目" description="项目编号、项目名称、评估类型、维护人员、启用时间和项目状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CENTER', 'ASSESSOR', 'THERAPIST']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addAssessmentItem, deleteAssessmentItem, finishAssessmentItem, getAssessmentItemPage, processAssessmentItem, updateAssessmentItem } from '../api'
const api = { page: getAssessmentItemPage, add: addAssessmentItem, update: updateAssessmentItem, delete: deleteAssessmentItem, process: processAssessmentItem, finish: finishAssessmentItem }
const columns = [{ prop: 'recordNo', label: '项目编号' }, { prop: 'recordName', label: '项目名称' }, { prop: 'category', label: '评估类型' }, { prop: 'ownerName', label: '维护人员' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '项目状态' }, { prop: 'remark', label: '项目说明' }]
const formFields = [{ prop: 'recordNo', label: '项目编号' }, { prop: 'recordName', label: '项目名称' }, { prop: 'category', label: '评估类型' }, { prop: 'ownerName', label: '维护人员' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '项目状态', type: 'select' }, { prop: 'remark', label: '项目说明', type: 'textarea' }]
</script>

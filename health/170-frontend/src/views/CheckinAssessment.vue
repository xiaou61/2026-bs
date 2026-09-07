<template><DataPage title="入住评估" description="评估编号、评估老人、评估类型、评估人员、评估时间和评估状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'ADMISSION', 'NURSING', 'DIRECTOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addCheckinAssessment, deleteCheckinAssessment, finishCheckinAssessment, getCheckinAssessmentPage, processCheckinAssessment, updateCheckinAssessment } from '../api'
const api = { page: getCheckinAssessmentPage, add: addCheckinAssessment, update: updateCheckinAssessment, delete: deleteCheckinAssessment, process: processCheckinAssessment, finish: finishCheckinAssessment }
const columns = [{ prop: 'recordNo', label: '评估编号' }, { prop: 'recordName', label: '评估老人' }, { prop: 'category', label: '评估类型' }, { prop: 'ownerName', label: '评估人员' }, { prop: 'planTime', label: '评估时间' }, { prop: 'status', label: '评估状态' }, { prop: 'remark', label: '评估说明' }]
const formFields = [{ prop: 'recordNo', label: '评估编号' }, { prop: 'recordName', label: '评估老人' }, { prop: 'category', label: '评估类型' }, { prop: 'ownerName', label: '评估人员' }, { prop: 'planTime', label: '评估时间' }, { prop: 'status', label: '评估状态', type: 'select' }, { prop: 'remark', label: '评估说明', type: 'textarea' }]
</script>

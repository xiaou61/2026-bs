<template><DataPage title="培训通知" description="通知编号、通知对象、通知类型、发布人员、发布时间和通知状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'EDU', 'INSTRUCTOR', 'ASSESSOR', 'NURSE', 'HR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addTrainingNotice, deleteTrainingNotice, finishTrainingNotice, getTrainingNoticePage, processTrainingNotice, updateTrainingNotice } from '../api'
const api = { page: getTrainingNoticePage, add: addTrainingNotice, update: updateTrainingNotice, delete: deleteTrainingNotice, process: processTrainingNotice, finish: finishTrainingNotice }
const columns = [{ prop: 'recordNo', label: '通知编号' }, { prop: 'recordName', label: '通知对象' }, { prop: 'category', label: '通知类型' }, { prop: 'ownerName', label: '发布人员' }, { prop: 'planTime', label: '发布时间' }, { prop: 'status', label: '通知状态' }, { prop: 'remark', label: '通知说明' }]
const formFields = [{ prop: 'recordNo', label: '通知编号' }, { prop: 'recordName', label: '通知对象' }, { prop: 'category', label: '通知类型' }, { prop: 'ownerName', label: '发布人员' }, { prop: 'planTime', label: '发布时间' }, { prop: 'status', label: '通知状态', type: 'select' }, { prop: 'remark', label: '通知说明', type: 'textarea' }]
</script>

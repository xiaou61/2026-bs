<template><DataPage title="学员档案" description="学员编号、学员姓名、职业方向、联系人员、建档时间和学员状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'TRAINING', 'INSTRUCTOR', 'TRAINEE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addTraineeProfile, deleteTraineeProfile, finishTraineeProfile, getTraineeProfilePage, processTraineeProfile, updateTraineeProfile } from '../api'
const api = { page: getTraineeProfilePage, add: addTraineeProfile, update: updateTraineeProfile, delete: deleteTraineeProfile, process: processTraineeProfile, finish: finishTraineeProfile }
const columns = [{ prop: 'recordNo', label: '学员编号' }, { prop: 'recordName', label: '学员姓名' }, { prop: 'category', label: '职业方向' }, { prop: 'ownerName', label: '联系人员' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '学员状态' }, { prop: 'remark', label: '学员说明' }]
const formFields = [{ prop: 'recordNo', label: '学员编号' }, { prop: 'recordName', label: '学员姓名' }, { prop: 'category', label: '职业方向' }, { prop: 'ownerName', label: '联系人员' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '学员状态', type: 'select' }, { prop: 'remark', label: '学员说明', type: 'textarea' }]
</script>

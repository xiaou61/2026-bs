<template><DataPage title="报名选拔" description="报名编号、报名学生、申报方向、申请人员、报名时间和报名状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'ACADEMIC', 'REVIEWER', 'COUNSELOR', 'STUDENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addApplicationRegistration, deleteApplicationRegistration, finishApplicationRegistration, getApplicationRegistrationPage, processApplicationRegistration, updateApplicationRegistration } from '../api'
const api = { page: getApplicationRegistrationPage, add: addApplicationRegistration, update: updateApplicationRegistration, delete: deleteApplicationRegistration, process: processApplicationRegistration, finish: finishApplicationRegistration }
const columns = [{ prop: 'recordNo', label: '报名编号' }, { prop: 'recordName', label: '报名学生' }, { prop: 'category', label: '申报方向' }, { prop: 'ownerName', label: '申请人员' }, { prop: 'planTime', label: '报名时间' }, { prop: 'status', label: '报名状态' }, { prop: 'remark', label: '报名说明' }]
const formFields = [{ prop: 'recordNo', label: '报名编号' }, { prop: 'recordName', label: '报名学生' }, { prop: 'category', label: '申报方向' }, { prop: 'ownerName', label: '申请人员' }, { prop: 'planTime', label: '报名时间' }, { prop: 'status', label: '报名状态', type: 'select' }, { prop: 'remark', label: '报名说明', type: 'textarea' }]
</script>

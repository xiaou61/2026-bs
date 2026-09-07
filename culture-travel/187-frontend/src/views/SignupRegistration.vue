<template><DataPage title="行程报名" description="报名编号、关联行程、报名类型、报名人员、报名时间和报名状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'AGENCY', 'PLANNER', 'LEADER', 'PARTICIPANT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addSignupRegistration, deleteSignupRegistration, finishSignupRegistration, getSignupRegistrationPage, processSignupRegistration, updateSignupRegistration } from '../api'
const api = { page: getSignupRegistrationPage, add: addSignupRegistration, update: updateSignupRegistration, delete: deleteSignupRegistration, process: processSignupRegistration, finish: finishSignupRegistration }
const columns = [{ prop: 'recordNo', label: '报名编号' }, { prop: 'recordName', label: '关联行程' }, { prop: 'category', label: '报名类型' }, { prop: 'ownerName', label: '报名人员' }, { prop: 'planTime', label: '报名时间' }, { prop: 'status', label: '报名状态' }, { prop: 'remark', label: '报名说明' }]
const formFields = [{ prop: 'recordNo', label: '报名编号' }, { prop: 'recordName', label: '关联行程' }, { prop: 'category', label: '报名类型' }, { prop: 'ownerName', label: '报名人员' }, { prop: 'planTime', label: '报名时间' }, { prop: 'status', label: '报名状态', type: 'select' }, { prop: 'remark', label: '报名说明', type: 'textarea' }]
</script>

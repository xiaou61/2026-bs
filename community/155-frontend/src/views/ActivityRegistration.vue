<template><DataPage title="活动报名" description="报名编号、党建活动、报名类型、报名人、报名时间和报名状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'ORGANIZER', 'VOLUNTEER', 'RESIDENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addActivityRegistration, deleteActivityRegistration, finishActivityRegistration, getActivityRegistrationPage, processActivityRegistration, updateActivityRegistration } from '../api'
const api = { page: getActivityRegistrationPage, add: addActivityRegistration, update: updateActivityRegistration, delete: deleteActivityRegistration, process: processActivityRegistration, finish: finishActivityRegistration }
const columns = [{ prop: 'recordNo', label: '报名编号' }, { prop: 'recordName', label: '党建活动' }, { prop: 'category', label: '报名类型' }, { prop: 'ownerName', label: '报名人' }, { prop: 'planTime', label: '报名时间' }, { prop: 'status', label: '报名状态' }, { prop: 'remark', label: '报名备注' }]
const formFields = [{ prop: 'recordNo', label: '报名编号' }, { prop: 'recordName', label: '党建活动' }, { prop: 'category', label: '报名类型' }, { prop: 'ownerName', label: '报名人' }, { prop: 'planTime', label: '报名时间' }, { prop: 'status', label: '报名状态', type: 'select' }, { prop: 'remark', label: '报名备注', type: 'textarea' }]
</script>

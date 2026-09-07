<template><DataPage title="教练档案" description="教练编号、教练姓名、擅长项目、管理人员、入职时间和教练状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CENTER', 'COACH']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addCoachProfile, deleteCoachProfile, finishCoachProfile, getCoachProfilePage, processCoachProfile, updateCoachProfile } from '../api'
const api = { page: getCoachProfilePage, add: addCoachProfile, update: updateCoachProfile, delete: deleteCoachProfile, process: processCoachProfile, finish: finishCoachProfile }
const columns = [{ prop: 'recordNo', label: '教练编号' }, { prop: 'recordName', label: '教练姓名' }, { prop: 'category', label: '擅长项目' }, { prop: 'ownerName', label: '管理人员' }, { prop: 'planTime', label: '入职时间' }, { prop: 'status', label: '教练状态' }, { prop: 'remark', label: '教练说明' }]
const formFields = [{ prop: 'recordNo', label: '教练编号' }, { prop: 'recordName', label: '教练姓名' }, { prop: 'category', label: '擅长项目' }, { prop: 'ownerName', label: '管理人员' }, { prop: 'planTime', label: '入职时间' }, { prop: 'status', label: '教练状态', type: 'select' }, { prop: 'remark', label: '教练说明', type: 'textarea' }]
</script>

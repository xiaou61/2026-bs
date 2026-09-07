<template><DataPage title="人员档案" description="人员编号、人员姓名、技能类型、管理人员、入职时间和人员状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'AGENCY', 'DISPATCH', 'WORKER', 'QUALITY']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addWorkerProfile, deleteWorkerProfile, finishWorkerProfile, getWorkerProfilePage, processWorkerProfile, updateWorkerProfile } from '../api'
const api = { page: getWorkerProfilePage, add: addWorkerProfile, update: updateWorkerProfile, delete: deleteWorkerProfile, process: processWorkerProfile, finish: finishWorkerProfile }
const columns = [{ prop: 'recordNo', label: '人员编号' }, { prop: 'recordName', label: '人员姓名' }, { prop: 'category', label: '技能类型' }, { prop: 'ownerName', label: '管理人员' }, { prop: 'planTime', label: '入职时间' }, { prop: 'status', label: '人员状态' }, { prop: 'remark', label: '人员说明' }]
const formFields = [{ prop: 'recordNo', label: '人员编号' }, { prop: 'recordName', label: '人员姓名' }, { prop: 'category', label: '技能类型' }, { prop: 'ownerName', label: '管理人员' }, { prop: 'planTime', label: '入职时间' }, { prop: 'status', label: '人员状态', type: 'select' }, { prop: 'remark', label: '人员说明', type: 'textarea' }]
</script>

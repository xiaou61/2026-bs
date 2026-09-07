<template><DataPage title="作业人员档案" description="工号、姓名、资质等级、所属班组、有效期和资质状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'SAFETY', 'WORKER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addWorkerProfile, deleteWorkerProfile, finishWorkerProfile, getWorkerProfilePage, processWorkerProfile, updateWorkerProfile } from '../api'
const api = { page: getWorkerProfilePage, add: addWorkerProfile, update: updateWorkerProfile, delete: deleteWorkerProfile, process: processWorkerProfile, finish: finishWorkerProfile }
const columns = [{ prop: 'recordNo', label: '工号' }, { prop: 'recordName', label: '姓名' }, { prop: 'category', label: '资质等级' }, { prop: 'ownerName', label: '所属班组' }, { prop: 'planTime', label: '有效期' }, { prop: 'status', label: '资质状态' }, { prop: 'remark', label: '资质说明' }]
const formFields = [{ prop: 'recordNo', label: '工号' }, { prop: 'recordName', label: '姓名' }, { prop: 'category', label: '资质等级' }, { prop: 'ownerName', label: '所属班组' }, { prop: 'planTime', label: '有效期' }, { prop: 'status', label: '资质状态', type: 'select' }, { prop: 'remark', label: '资质说明', type: 'textarea' }]
</script>

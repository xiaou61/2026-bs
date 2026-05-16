<template><DataPage title="床位档案" description="床位编号、床位名称、床位类型、管理人员、投用时间和床位状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'ADMISSION', 'NURSING', 'DIRECTOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addBedProfile, deleteBedProfile, finishBedProfile, getBedProfilePage, processBedProfile, updateBedProfile } from '../api'
const api = { page: getBedProfilePage, add: addBedProfile, update: updateBedProfile, delete: deleteBedProfile, process: processBedProfile, finish: finishBedProfile }
const columns = [{ prop: 'recordNo', label: '床位编号' }, { prop: 'recordName', label: '床位名称' }, { prop: 'category', label: '床位类型' }, { prop: 'ownerName', label: '管理人员' }, { prop: 'planTime', label: '投用时间' }, { prop: 'status', label: '床位状态' }, { prop: 'remark', label: '床位说明' }]
const formFields = [{ prop: 'recordNo', label: '床位编号' }, { prop: 'recordName', label: '床位名称' }, { prop: 'category', label: '床位类型' }, { prop: 'ownerName', label: '管理人员' }, { prop: 'planTime', label: '投用时间' }, { prop: 'status', label: '床位状态', type: 'select' }, { prop: 'remark', label: '床位说明', type: 'textarea' }]
</script>

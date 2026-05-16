<template><DataPage title="护工档案" description="护工编号、护工姓名、技能类别、所属机构、入职时间和在岗状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'HOSPITAL', 'COORDINATOR', 'CAREGIVER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addCaregiverProfile, deleteCaregiverProfile, finishCaregiverProfile, getCaregiverProfilePage, processCaregiverProfile, updateCaregiverProfile } from '../api'
const api = { page: getCaregiverProfilePage, add: addCaregiverProfile, update: updateCaregiverProfile, delete: deleteCaregiverProfile, process: processCaregiverProfile, finish: finishCaregiverProfile }
const columns = [{ prop: 'recordNo', label: '护工编号' }, { prop: 'recordName', label: '护工姓名' }, { prop: 'category', label: '技能类别' }, { prop: 'ownerName', label: '所属机构' }, { prop: 'planTime', label: '入职时间' }, { prop: 'status', label: '在岗状态' }, { prop: 'remark', label: '护工说明' }]
const formFields = [{ prop: 'recordNo', label: '护工编号' }, { prop: 'recordName', label: '护工姓名' }, { prop: 'category', label: '技能类别' }, { prop: 'ownerName', label: '所属机构' }, { prop: 'planTime', label: '入职时间' }, { prop: 'status', label: '在岗状态', type: 'select' }, { prop: 'remark', label: '护工说明', type: 'textarea' }]
</script>

<template><DataPage title="毕业生档案" description="学生编号、学生姓名、毕业专业、辅导员、建档时间和学生状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CAREER', 'COLLEGE', 'COUNSELOR', 'STUDENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addGraduateProfile, deleteGraduateProfile, finishGraduateProfile, getGraduateProfilePage, processGraduateProfile, updateGraduateProfile } from '../api'
const api = { page: getGraduateProfilePage, add: addGraduateProfile, update: updateGraduateProfile, delete: deleteGraduateProfile, process: processGraduateProfile, finish: finishGraduateProfile }
const columns = [{ prop: 'recordNo', label: '学生编号' }, { prop: 'recordName', label: '学生姓名' }, { prop: 'category', label: '毕业专业' }, { prop: 'ownerName', label: '辅导员' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '学生状态' }, { prop: 'remark', label: '学生说明' }]
const formFields = [{ prop: 'recordNo', label: '学生编号' }, { prop: 'recordName', label: '学生姓名' }, { prop: 'category', label: '毕业专业' }, { prop: 'ownerName', label: '辅导员' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '学生状态', type: 'select' }, { prop: 'remark', label: '学生说明', type: 'textarea' }]
</script>

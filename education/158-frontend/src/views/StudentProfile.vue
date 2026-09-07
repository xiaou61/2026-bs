<template><DataPage title="学员档案" description="学员编号、学员姓名、报名课程、监护人、报名时间和学籍状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'PRINCIPAL', 'ACADEMIC', 'PARENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addStudentProfile, deleteStudentProfile, finishStudentProfile, getStudentProfilePage, processStudentProfile, updateStudentProfile } from '../api'
const api = { page: getStudentProfilePage, add: addStudentProfile, update: updateStudentProfile, delete: deleteStudentProfile, process: processStudentProfile, finish: finishStudentProfile }
const columns = [{ prop: 'recordNo', label: '学员编号' }, { prop: 'recordName', label: '学员姓名' }, { prop: 'category', label: '报名课程' }, { prop: 'ownerName', label: '监护人' }, { prop: 'planTime', label: '报名时间' }, { prop: 'status', label: '学籍状态' }, { prop: 'remark', label: '学员说明' }]
const formFields = [{ prop: 'recordNo', label: '学员编号' }, { prop: 'recordName', label: '学员姓名' }, { prop: 'category', label: '报名课程' }, { prop: 'ownerName', label: '监护人' }, { prop: 'planTime', label: '报名时间' }, { prop: 'status', label: '学籍状态', type: 'select' }, { prop: 'remark', label: '学员说明', type: 'textarea' }]
</script>

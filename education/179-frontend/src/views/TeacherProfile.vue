<template><DataPage title="教师档案" description="教师工号、教师姓名、教师类型、教务人员、任职时间和教师状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'ACADEMIC', 'TEACHER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addTeacherProfile, deleteTeacherProfile, finishTeacherProfile, getTeacherProfilePage, processTeacherProfile, updateTeacherProfile } from '../api'
const api = { page: getTeacherProfilePage, add: addTeacherProfile, update: updateTeacherProfile, delete: deleteTeacherProfile, process: processTeacherProfile, finish: finishTeacherProfile }
const columns = [{ prop: 'recordNo', label: '教师工号' }, { prop: 'recordName', label: '教师姓名' }, { prop: 'category', label: '教师类型' }, { prop: 'ownerName', label: '教务人员' }, { prop: 'planTime', label: '任职时间' }, { prop: 'status', label: '教师状态' }, { prop: 'remark', label: '教师说明' }]
const formFields = [{ prop: 'recordNo', label: '教师工号' }, { prop: 'recordName', label: '教师姓名' }, { prop: 'category', label: '教师类型' }, { prop: 'ownerName', label: '教务人员' }, { prop: 'planTime', label: '任职时间' }, { prop: 'status', label: '教师状态', type: 'select' }, { prop: 'remark', label: '教师说明', type: 'textarea' }]
</script>

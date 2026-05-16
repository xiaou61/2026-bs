<template><DataPage title="教师档案" description="教师编号、教师姓名、授课类型、所属校区、入职时间和教师状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'PRINCIPAL', 'ACADEMIC', 'TEACHER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addTeacherProfile, deleteTeacherProfile, finishTeacherProfile, getTeacherProfilePage, processTeacherProfile, updateTeacherProfile } from '../api'
const api = { page: getTeacherProfilePage, add: addTeacherProfile, update: updateTeacherProfile, delete: deleteTeacherProfile, process: processTeacherProfile, finish: finishTeacherProfile }
const columns = [{ prop: 'recordNo', label: '教师编号' }, { prop: 'recordName', label: '教师姓名' }, { prop: 'category', label: '授课类型' }, { prop: 'ownerName', label: '所属校区' }, { prop: 'planTime', label: '入职时间' }, { prop: 'status', label: '教师状态' }, { prop: 'remark', label: '教师说明' }]
const formFields = [{ prop: 'recordNo', label: '教师编号' }, { prop: 'recordName', label: '教师姓名' }, { prop: 'category', label: '授课类型' }, { prop: 'ownerName', label: '所属校区' }, { prop: 'planTime', label: '入职时间' }, { prop: 'status', label: '教师状态', type: 'select' }, { prop: 'remark', label: '教师说明', type: 'textarea' }]
</script>

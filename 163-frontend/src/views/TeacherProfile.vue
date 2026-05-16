<template><DataPage title="带教老师" description="工号、老师姓名、带教科室、职称、带教时间和在岗状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'SECRETARY', 'DIRECTOR', 'TEACHER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addTeacherProfile, deleteTeacherProfile, finishTeacherProfile, getTeacherProfilePage, processTeacherProfile, updateTeacherProfile } from '../api'
const api = { page: getTeacherProfilePage, add: addTeacherProfile, update: updateTeacherProfile, delete: deleteTeacherProfile, process: processTeacherProfile, finish: finishTeacherProfile }
const columns = [{ prop: 'recordNo', label: '工号' }, { prop: 'recordName', label: '老师姓名' }, { prop: 'category', label: '带教科室' }, { prop: 'ownerName', label: '职称' }, { prop: 'planTime', label: '带教时间' }, { prop: 'status', label: '在岗状态' }, { prop: 'remark', label: '老师说明' }]
const formFields = [{ prop: 'recordNo', label: '工号' }, { prop: 'recordName', label: '老师姓名' }, { prop: 'category', label: '带教科室' }, { prop: 'ownerName', label: '职称' }, { prop: 'planTime', label: '带教时间' }, { prop: 'status', label: '在岗状态', type: 'select' }, { prop: 'remark', label: '老师说明', type: 'textarea' }]
</script>

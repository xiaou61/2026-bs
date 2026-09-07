<template><DataPage title="学生档案" description="学生编号、学生姓名、年级班级、班主任、建档时间和学生状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'SCHOOL', 'TEACHER', 'GUARDIAN']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addStudentProfile, deleteStudentProfile, finishStudentProfile, getStudentProfilePage, processStudentProfile, updateStudentProfile } from '../api'
const api = { page: getStudentProfilePage, add: addStudentProfile, update: updateStudentProfile, delete: deleteStudentProfile, process: processStudentProfile, finish: finishStudentProfile }
const columns = [{ prop: 'recordNo', label: '学生编号' }, { prop: 'recordName', label: '学生姓名' }, { prop: 'category', label: '年级班级' }, { prop: 'ownerName', label: '班主任' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '学生状态' }, { prop: 'remark', label: '学生说明' }]
const formFields = [{ prop: 'recordNo', label: '学生编号' }, { prop: 'recordName', label: '学生姓名' }, { prop: 'category', label: '年级班级' }, { prop: 'ownerName', label: '班主任' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '学生状态', type: 'select' }, { prop: 'remark', label: '学生说明', type: 'textarea' }]
</script>

<template><DataPage title="学生档案" description="学号、姓名、学院班级、联系方式、认证时间和账号状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'OPERATOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addStudentProfile, deleteStudentProfile, finishStudentProfile, getStudentProfilePage, processStudentProfile, updateStudentProfile } from '../api'
const api = { page: getStudentProfilePage, add: addStudentProfile, update: updateStudentProfile, delete: deleteStudentProfile, process: processStudentProfile, finish: finishStudentProfile }
const columns = [{ prop: 'recordNo', label: '学号' }, { prop: 'recordName', label: '姓名' }, { prop: 'category', label: '学院班级' }, { prop: 'ownerName', label: '联系方式' }, { prop: 'planTime', label: '认证时间' }, { prop: 'status', label: '账号状态' }, { prop: 'remark', label: '认证说明' }]
const formFields = [{ prop: 'recordNo', label: '学号' }, { prop: 'recordName', label: '姓名' }, { prop: 'category', label: '学院班级' }, { prop: 'ownerName', label: '联系方式' }, { prop: 'planTime', label: '认证时间' }, { prop: 'status', label: '账号状态', type: 'select' }, { prop: 'remark', label: '认证说明', type: 'textarea' }]
</script>

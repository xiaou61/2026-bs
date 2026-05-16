<template><DataPage title="实习学生" description="学号、学生姓名、专业年级、导师、入院时间和实习状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'SECRETARY', 'TEACHER', 'STUDENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addStudentProfile, deleteStudentProfile, finishStudentProfile, getStudentProfilePage, processStudentProfile, updateStudentProfile } from '../api'
const api = { page: getStudentProfilePage, add: addStudentProfile, update: updateStudentProfile, delete: deleteStudentProfile, process: processStudentProfile, finish: finishStudentProfile }
const columns = [{ prop: 'recordNo', label: '学号' }, { prop: 'recordName', label: '学生姓名' }, { prop: 'category', label: '专业年级' }, { prop: 'ownerName', label: '导师' }, { prop: 'planTime', label: '入院时间' }, { prop: 'status', label: '实习状态' }, { prop: 'remark', label: '学生说明' }]
const formFields = [{ prop: 'recordNo', label: '学号' }, { prop: 'recordName', label: '学生姓名' }, { prop: 'category', label: '专业年级' }, { prop: 'ownerName', label: '导师' }, { prop: 'planTime', label: '入院时间' }, { prop: 'status', label: '实习状态', type: 'select' }, { prop: 'remark', label: '学生说明', type: 'textarea' }]
</script>

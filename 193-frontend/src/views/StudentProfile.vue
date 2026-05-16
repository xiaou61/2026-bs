<template><DataPage title="学生档案" description="档案编号、学生姓名、专业年级、辅导员、入学时间和培养状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'ACADEMIC', 'COUNSELOR', 'STUDENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addStudentProfile, deleteStudentProfile, finishStudentProfile, getStudentProfilePage, processStudentProfile, updateStudentProfile } from '../api'
const api = { page: getStudentProfilePage, add: addStudentProfile, update: updateStudentProfile, delete: deleteStudentProfile, process: processStudentProfile, finish: finishStudentProfile }
const columns = [{ prop: 'recordNo', label: '档案编号' }, { prop: 'recordName', label: '学生姓名' }, { prop: 'category', label: '专业年级' }, { prop: 'ownerName', label: '辅导员' }, { prop: 'planTime', label: '入学时间' }, { prop: 'status', label: '培养状态' }, { prop: 'remark', label: '档案说明' }]
const formFields = [{ prop: 'recordNo', label: '档案编号' }, { prop: 'recordName', label: '学生姓名' }, { prop: 'category', label: '专业年级' }, { prop: 'ownerName', label: '辅导员' }, { prop: 'planTime', label: '入学时间' }, { prop: 'status', label: '培养状态', type: 'select' }, { prop: 'remark', label: '档案说明', type: 'textarea' }]
</script>

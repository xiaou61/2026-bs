<template><DataPage title="教学班级" description="班级编号、班级名称、课程类型、教务人员、开课时间和班级状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'ACADEMIC', 'TEACHER', 'COUNSELOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addTeachingClass, deleteTeachingClass, finishTeachingClass, getTeachingClassPage, processTeachingClass, updateTeachingClass } from '../api'
const api = { page: getTeachingClassPage, add: addTeachingClass, update: updateTeachingClass, delete: deleteTeachingClass, process: processTeachingClass, finish: finishTeachingClass }
const columns = [{ prop: 'recordNo', label: '班级编号' }, { prop: 'recordName', label: '班级名称' }, { prop: 'category', label: '课程类型' }, { prop: 'ownerName', label: '教务人员' }, { prop: 'planTime', label: '开课时间' }, { prop: 'status', label: '班级状态' }, { prop: 'remark', label: '班级说明' }]
const formFields = [{ prop: 'recordNo', label: '班级编号' }, { prop: 'recordName', label: '班级名称' }, { prop: 'category', label: '课程类型' }, { prop: 'ownerName', label: '教务人员' }, { prop: 'planTime', label: '开课时间' }, { prop: 'status', label: '班级状态', type: 'select' }, { prop: 'remark', label: '班级说明', type: 'textarea' }]
</script>

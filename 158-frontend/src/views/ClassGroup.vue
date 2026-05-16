<template><DataPage title="班级台账" description="班级编号、班级名称、班级类型、班主任、开班时间和班级状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'PRINCIPAL', 'ACADEMIC', 'TEACHER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addClassGroup, deleteClassGroup, finishClassGroup, getClassGroupPage, processClassGroup, updateClassGroup } from '../api'
const api = { page: getClassGroupPage, add: addClassGroup, update: updateClassGroup, delete: deleteClassGroup, process: processClassGroup, finish: finishClassGroup }
const columns = [{ prop: 'recordNo', label: '班级编号' }, { prop: 'recordName', label: '班级名称' }, { prop: 'category', label: '班级类型' }, { prop: 'ownerName', label: '班主任' }, { prop: 'planTime', label: '开班时间' }, { prop: 'status', label: '班级状态' }, { prop: 'remark', label: '班级说明' }]
const formFields = [{ prop: 'recordNo', label: '班级编号' }, { prop: 'recordName', label: '班级名称' }, { prop: 'category', label: '班级类型' }, { prop: 'ownerName', label: '班主任' }, { prop: 'planTime', label: '开班时间' }, { prop: 'status', label: '班级状态', type: 'select' }, { prop: 'remark', label: '班级说明', type: 'textarea' }]
</script>

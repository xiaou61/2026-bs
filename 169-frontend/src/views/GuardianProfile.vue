<template><DataPage title="家长档案" description="家长编号、家长姓名、关联学生、联系人员、建档时间和家长状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'SCHOOL', 'TEACHER', 'GUARDIAN']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addGuardianProfile, deleteGuardianProfile, finishGuardianProfile, getGuardianProfilePage, processGuardianProfile, updateGuardianProfile } from '../api'
const api = { page: getGuardianProfilePage, add: addGuardianProfile, update: updateGuardianProfile, delete: deleteGuardianProfile, process: processGuardianProfile, finish: finishGuardianProfile }
const columns = [{ prop: 'recordNo', label: '家长编号' }, { prop: 'recordName', label: '家长姓名' }, { prop: 'category', label: '关联学生' }, { prop: 'ownerName', label: '联系人员' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '家长状态' }, { prop: 'remark', label: '家长说明' }]
const formFields = [{ prop: 'recordNo', label: '家长编号' }, { prop: 'recordName', label: '家长姓名' }, { prop: 'category', label: '关联学生' }, { prop: 'ownerName', label: '联系人员' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '家长状态', type: 'select' }, { prop: 'remark', label: '家长说明', type: 'textarea' }]
</script>

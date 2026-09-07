<template><DataPage title="监护安排" description="安排编号、作业票号、监护类型、监护人、监护时段和监护状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'SAFETY', 'GUARDIAN']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addGuardianAssignment, deleteGuardianAssignment, finishGuardianAssignment, getGuardianAssignmentPage, processGuardianAssignment, updateGuardianAssignment } from '../api'
const api = { page: getGuardianAssignmentPage, add: addGuardianAssignment, update: updateGuardianAssignment, delete: deleteGuardianAssignment, process: processGuardianAssignment, finish: finishGuardianAssignment }
const columns = [{ prop: 'recordNo', label: '安排编号' }, { prop: 'recordName', label: '作业票号' }, { prop: 'category', label: '监护类型' }, { prop: 'ownerName', label: '监护人' }, { prop: 'planTime', label: '监护时段' }, { prop: 'status', label: '监护状态' }, { prop: 'remark', label: '安排说明' }]
const formFields = [{ prop: 'recordNo', label: '安排编号' }, { prop: 'recordName', label: '作业票号' }, { prop: 'category', label: '监护类型' }, { prop: 'ownerName', label: '监护人' }, { prop: 'planTime', label: '监护时段' }, { prop: 'status', label: '监护状态', type: 'select' }, { prop: 'remark', label: '安排说明', type: 'textarea' }]
</script>

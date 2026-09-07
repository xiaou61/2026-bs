<template><DataPage title="裁判指派" description="指派编号、比赛项目、裁判岗位、裁判员、指派时间和指派状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'ORGANIZER', 'REFEREE', 'COMMITTEE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addRefereeAssignment, deleteRefereeAssignment, finishRefereeAssignment, getRefereeAssignmentPage, processRefereeAssignment, updateRefereeAssignment } from '../api'
const api = { page: getRefereeAssignmentPage, add: addRefereeAssignment, update: updateRefereeAssignment, delete: deleteRefereeAssignment, process: processRefereeAssignment, finish: finishRefereeAssignment }
const columns = [{ prop: 'recordNo', label: '指派编号' }, { prop: 'recordName', label: '比赛项目' }, { prop: 'category', label: '裁判岗位' }, { prop: 'ownerName', label: '裁判员' }, { prop: 'planTime', label: '指派时间' }, { prop: 'status', label: '指派状态' }, { prop: 'remark', label: '指派说明' }]
const formFields = [{ prop: 'recordNo', label: '指派编号' }, { prop: 'recordName', label: '比赛项目' }, { prop: 'category', label: '裁判岗位' }, { prop: 'ownerName', label: '裁判员' }, { prop: 'planTime', label: '指派时间' }, { prop: 'status', label: '指派状态', type: 'select' }, { prop: 'remark', label: '指派说明', type: 'textarea' }]
</script>

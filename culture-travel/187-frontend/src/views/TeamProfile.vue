<template><DataPage title="团队档案" description="团队编号、团队名称、团队类型、领队人员、建档时间和团队状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'AGENCY', 'LEADER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addTeamProfile, deleteTeamProfile, finishTeamProfile, getTeamProfilePage, processTeamProfile, updateTeamProfile } from '../api'
const api = { page: getTeamProfilePage, add: addTeamProfile, update: updateTeamProfile, delete: deleteTeamProfile, process: processTeamProfile, finish: finishTeamProfile }
const columns = [{ prop: 'recordNo', label: '团队编号' }, { prop: 'recordName', label: '团队名称' }, { prop: 'category', label: '团队类型' }, { prop: 'ownerName', label: '领队人员' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '团队状态' }, { prop: 'remark', label: '团队说明' }]
const formFields = [{ prop: 'recordNo', label: '团队编号' }, { prop: 'recordName', label: '团队名称' }, { prop: 'category', label: '团队类型' }, { prop: 'ownerName', label: '领队人员' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '团队状态', type: 'select' }, { prop: 'remark', label: '团队说明', type: 'textarea' }]
</script>

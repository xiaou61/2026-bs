<template><DataPage title="参赛队伍" description="队伍编号、队伍名称、参赛组别、队伍教练、报名时间和队伍状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'ORGANIZER', 'COACH']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addTeamProfile, deleteTeamProfile, finishTeamProfile, getTeamProfilePage, processTeamProfile, updateTeamProfile } from '../api'
const api = { page: getTeamProfilePage, add: addTeamProfile, update: updateTeamProfile, delete: deleteTeamProfile, process: processTeamProfile, finish: finishTeamProfile }
const columns = [{ prop: 'recordNo', label: '队伍编号' }, { prop: 'recordName', label: '队伍名称' }, { prop: 'category', label: '参赛组别' }, { prop: 'ownerName', label: '队伍教练' }, { prop: 'planTime', label: '报名时间' }, { prop: 'status', label: '队伍状态' }, { prop: 'remark', label: '队伍说明' }]
const formFields = [{ prop: 'recordNo', label: '队伍编号' }, { prop: 'recordName', label: '队伍名称' }, { prop: 'category', label: '参赛组别' }, { prop: 'ownerName', label: '队伍教练' }, { prop: 'planTime', label: '报名时间' }, { prop: 'status', label: '队伍状态', type: 'select' }, { prop: 'remark', label: '队伍说明', type: 'textarea' }]
</script>

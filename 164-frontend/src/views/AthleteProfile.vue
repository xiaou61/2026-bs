<template><DataPage title="运动员档案" description="学号、学生姓名、所属队伍、指导教练、登记时间和参赛状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'ORGANIZER', 'COACH', 'ATHLETE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addAthleteProfile, deleteAthleteProfile, finishAthleteProfile, getAthleteProfilePage, processAthleteProfile, updateAthleteProfile } from '../api'
const api = { page: getAthleteProfilePage, add: addAthleteProfile, update: updateAthleteProfile, delete: deleteAthleteProfile, process: processAthleteProfile, finish: finishAthleteProfile }
const columns = [{ prop: 'recordNo', label: '学号' }, { prop: 'recordName', label: '学生姓名' }, { prop: 'category', label: '所属队伍' }, { prop: 'ownerName', label: '指导教练' }, { prop: 'planTime', label: '登记时间' }, { prop: 'status', label: '参赛状态' }, { prop: 'remark', label: '档案说明' }]
const formFields = [{ prop: 'recordNo', label: '学号' }, { prop: 'recordName', label: '学生姓名' }, { prop: 'category', label: '所属队伍' }, { prop: 'ownerName', label: '指导教练' }, { prop: 'planTime', label: '登记时间' }, { prop: 'status', label: '参赛状态', type: 'select' }, { prop: 'remark', label: '档案说明', type: 'textarea' }]
</script>

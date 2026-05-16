<template><DataPage title="医护团队" description="团队编号、团队名称、服务类型、团队负责人、组建时间和团队状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CENTER', 'DOCTOR', 'NURSE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addDoctorTeam, deleteDoctorTeam, finishDoctorTeam, getDoctorTeamPage, processDoctorTeam, updateDoctorTeam } from '../api'
const api = { page: getDoctorTeamPage, add: addDoctorTeam, update: updateDoctorTeam, delete: deleteDoctorTeam, process: processDoctorTeam, finish: finishDoctorTeam }
const columns = [{ prop: 'recordNo', label: '团队编号' }, { prop: 'recordName', label: '团队名称' }, { prop: 'category', label: '服务类型' }, { prop: 'ownerName', label: '团队负责人' }, { prop: 'planTime', label: '组建时间' }, { prop: 'status', label: '团队状态' }, { prop: 'remark', label: '团队说明' }]
const formFields = [{ prop: 'recordNo', label: '团队编号' }, { prop: 'recordName', label: '团队名称' }, { prop: 'category', label: '服务类型' }, { prop: 'ownerName', label: '团队负责人' }, { prop: 'planTime', label: '组建时间' }, { prop: 'status', label: '团队状态', type: 'select' }, { prop: 'remark', label: '团队说明', type: 'textarea' }]
</script>

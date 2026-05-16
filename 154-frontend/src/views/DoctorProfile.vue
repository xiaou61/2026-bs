<template><DataPage title="医生档案" description="医生工号、医生姓名、擅长科室、所属诊室、坐诊时间和在岗状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'RECEPTION', 'DOCTOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addDoctorProfile, deleteDoctorProfile, finishDoctorProfile, getDoctorProfilePage, processDoctorProfile, updateDoctorProfile } from '../api'
const api = { page: getDoctorProfilePage, add: addDoctorProfile, update: updateDoctorProfile, delete: deleteDoctorProfile, process: processDoctorProfile, finish: finishDoctorProfile }
const columns = [{ prop: 'recordNo', label: '医生工号' }, { prop: 'recordName', label: '医生姓名' }, { prop: 'category', label: '擅长科室' }, { prop: 'ownerName', label: '所属诊室' }, { prop: 'planTime', label: '坐诊时间' }, { prop: 'status', label: '在岗状态' }, { prop: 'remark', label: '专长说明' }]
const formFields = [{ prop: 'recordNo', label: '医生工号' }, { prop: 'recordName', label: '医生姓名' }, { prop: 'category', label: '擅长科室' }, { prop: 'ownerName', label: '所属诊室' }, { prop: 'planTime', label: '坐诊时间' }, { prop: 'status', label: '在岗状态', type: 'select' }, { prop: 'remark', label: '专长说明', type: 'textarea' }]
</script>

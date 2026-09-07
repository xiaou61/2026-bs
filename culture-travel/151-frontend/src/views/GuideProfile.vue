<template><DataPage title="讲解员档案" description="讲解员工号、姓名、语种等级、所属团队、可服务时间和在岗状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'MANAGER', 'GUIDE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addGuideProfile, deleteGuideProfile, finishGuideProfile, getGuideProfilePage, processGuideProfile, updateGuideProfile } from '../api'
const api = { page: getGuideProfilePage, add: addGuideProfile, update: updateGuideProfile, delete: deleteGuideProfile, process: processGuideProfile, finish: finishGuideProfile }
const columns = [{ prop: 'recordNo', label: '讲解员工号' }, { prop: 'recordName', label: '讲解员姓名' }, { prop: 'category', label: '语种等级' }, { prop: 'ownerName', label: '所属团队' }, { prop: 'planTime', label: '可服务时间' }, { prop: 'status', label: '在岗状态' }, { prop: 'remark', label: '专长说明' }]
const formFields = [{ prop: 'recordNo', label: '讲解员工号' }, { prop: 'recordName', label: '讲解员姓名' }, { prop: 'category', label: '语种等级' }, { prop: 'ownerName', label: '所属团队' }, { prop: 'planTime', label: '可服务时间' }, { prop: 'status', label: '在岗状态', type: 'select' }, { prop: 'remark', label: '专长说明', type: 'textarea' }]
</script>

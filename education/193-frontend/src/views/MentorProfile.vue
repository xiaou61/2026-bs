<template><DataPage title="导师档案" description="导师编号、导师姓名、研究方向、所属学院、入库时间和导师状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'ACADEMIC', 'MENTOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addMentorProfile, deleteMentorProfile, finishMentorProfile, getMentorProfilePage, processMentorProfile, updateMentorProfile } from '../api'
const api = { page: getMentorProfilePage, add: addMentorProfile, update: updateMentorProfile, delete: deleteMentorProfile, process: processMentorProfile, finish: finishMentorProfile }
const columns = [{ prop: 'recordNo', label: '导师编号' }, { prop: 'recordName', label: '导师姓名' }, { prop: 'category', label: '研究方向' }, { prop: 'ownerName', label: '所属学院' }, { prop: 'planTime', label: '入库时间' }, { prop: 'status', label: '导师状态' }, { prop: 'remark', label: '导师说明' }]
const formFields = [{ prop: 'recordNo', label: '导师编号' }, { prop: 'recordName', label: '导师姓名' }, { prop: 'category', label: '研究方向' }, { prop: 'ownerName', label: '所属学院' }, { prop: 'planTime', label: '入库时间' }, { prop: 'status', label: '导师状态', type: 'select' }, { prop: 'remark', label: '导师说明', type: 'textarea' }]
</script>

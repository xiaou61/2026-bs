<template><DataPage title="导师匹配" description="匹配编号、入选学生、匹配方向、导师教师、匹配时间和匹配状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'ACADEMIC', 'MENTOR', 'COUNSELOR', 'STUDENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addMentorMatch, deleteMentorMatch, finishMentorMatch, getMentorMatchPage, processMentorMatch, updateMentorMatch } from '../api'
const api = { page: getMentorMatchPage, add: addMentorMatch, update: updateMentorMatch, delete: deleteMentorMatch, process: processMentorMatch, finish: finishMentorMatch }
const columns = [{ prop: 'recordNo', label: '匹配编号' }, { prop: 'recordName', label: '入选学生' }, { prop: 'category', label: '匹配方向' }, { prop: 'ownerName', label: '导师教师' }, { prop: 'planTime', label: '匹配时间' }, { prop: 'status', label: '匹配状态' }, { prop: 'remark', label: '匹配说明' }]
const formFields = [{ prop: 'recordNo', label: '匹配编号' }, { prop: 'recordName', label: '入选学生' }, { prop: 'category', label: '匹配方向' }, { prop: 'ownerName', label: '导师教师' }, { prop: 'planTime', label: '匹配时间' }, { prop: 'status', label: '匹配状态', type: 'select' }, { prop: 'remark', label: '匹配说明', type: 'textarea' }]
</script>

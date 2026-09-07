<template><DataPage title="赛程编排" description="赛程编号、比赛名称、竞赛轮次、编排人员、比赛时间和赛程状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'ORGANIZER', 'COACH', 'REFEREE', 'ATHLETE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addScheduleMatch, deleteScheduleMatch, finishScheduleMatch, getScheduleMatchPage, processScheduleMatch, updateScheduleMatch } from '../api'
const api = { page: getScheduleMatchPage, add: addScheduleMatch, update: updateScheduleMatch, delete: deleteScheduleMatch, process: processScheduleMatch, finish: finishScheduleMatch }
const columns = [{ prop: 'recordNo', label: '赛程编号' }, { prop: 'recordName', label: '比赛名称' }, { prop: 'category', label: '竞赛轮次' }, { prop: 'ownerName', label: '编排人员' }, { prop: 'planTime', label: '比赛时间' }, { prop: 'status', label: '赛程状态' }, { prop: 'remark', label: '赛程说明' }]
const formFields = [{ prop: 'recordNo', label: '赛程编号' }, { prop: 'recordName', label: '比赛名称' }, { prop: 'category', label: '竞赛轮次' }, { prop: 'ownerName', label: '编排人员' }, { prop: 'planTime', label: '比赛时间' }, { prop: 'status', label: '赛程状态', type: 'select' }, { prop: 'remark', label: '赛程说明', type: 'textarea' }]
</script>

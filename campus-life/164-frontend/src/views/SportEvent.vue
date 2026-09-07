<template><DataPage title="体育赛事" description="赛事编号、赛事名称、项目类型、主办老师、开赛时间和赛事状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'ORGANIZER', 'COMMITTEE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addSportEvent, deleteSportEvent, finishSportEvent, getSportEventPage, processSportEvent, updateSportEvent } from '../api'
const api = { page: getSportEventPage, add: addSportEvent, update: updateSportEvent, delete: deleteSportEvent, process: processSportEvent, finish: finishSportEvent }
const columns = [{ prop: 'recordNo', label: '赛事编号' }, { prop: 'recordName', label: '赛事名称' }, { prop: 'category', label: '项目类型' }, { prop: 'ownerName', label: '主办老师' }, { prop: 'planTime', label: '开赛时间' }, { prop: 'status', label: '赛事状态' }, { prop: 'remark', label: '赛事说明' }]
const formFields = [{ prop: 'recordNo', label: '赛事编号' }, { prop: 'recordName', label: '赛事名称' }, { prop: 'category', label: '项目类型' }, { prop: 'ownerName', label: '主办老师' }, { prop: 'planTime', label: '开赛时间' }, { prop: 'status', label: '赛事状态', type: 'select' }, { prop: 'remark', label: '赛事说明', type: 'textarea' }]
</script>

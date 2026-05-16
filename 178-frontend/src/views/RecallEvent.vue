<template><DataPage title="异常召回" description="召回编号、召回器械包、召回类型、质控人员、召回时间和召回状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'QA', 'CSSD', 'ORNURSE', 'SURGEON']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addRecallEvent, deleteRecallEvent, finishRecallEvent, getRecallEventPage, processRecallEvent, updateRecallEvent } from '../api'
const api = { page: getRecallEventPage, add: addRecallEvent, update: updateRecallEvent, delete: deleteRecallEvent, process: processRecallEvent, finish: finishRecallEvent }
const columns = [{ prop: 'recordNo', label: '召回编号' }, { prop: 'recordName', label: '召回器械包' }, { prop: 'category', label: '召回类型' }, { prop: 'ownerName', label: '质控人员' }, { prop: 'planTime', label: '召回时间' }, { prop: 'status', label: '召回状态' }, { prop: 'remark', label: '召回说明' }]
const formFields = [{ prop: 'recordNo', label: '召回编号' }, { prop: 'recordName', label: '召回器械包' }, { prop: 'category', label: '召回类型' }, { prop: 'ownerName', label: '质控人员' }, { prop: 'planTime', label: '召回时间' }, { prop: 'status', label: '召回状态', type: 'select' }, { prop: 'remark', label: '召回说明', type: 'textarea' }]
</script>

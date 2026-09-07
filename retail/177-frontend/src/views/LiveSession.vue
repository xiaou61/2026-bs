<template><DataPage title="直播场次" description="场次编号、直播主题、场次类型、主播人员、开播时间和场次状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'BASE', 'ANCHOR', 'SELECTOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addLiveSession, deleteLiveSession, finishLiveSession, getLiveSessionPage, processLiveSession, updateLiveSession } from '../api'
const api = { page: getLiveSessionPage, add: addLiveSession, update: updateLiveSession, delete: deleteLiveSession, process: processLiveSession, finish: finishLiveSession }
const columns = [{ prop: 'recordNo', label: '场次编号' }, { prop: 'recordName', label: '直播主题' }, { prop: 'category', label: '场次类型' }, { prop: 'ownerName', label: '主播人员' }, { prop: 'planTime', label: '开播时间' }, { prop: 'status', label: '场次状态' }, { prop: 'remark', label: '场次说明' }]
const formFields = [{ prop: 'recordNo', label: '场次编号' }, { prop: 'recordName', label: '直播主题' }, { prop: 'category', label: '场次类型' }, { prop: 'ownerName', label: '主播人员' }, { prop: 'planTime', label: '开播时间' }, { prop: 'status', label: '场次状态', type: 'select' }, { prop: 'remark', label: '场次说明', type: 'textarea' }]
</script>

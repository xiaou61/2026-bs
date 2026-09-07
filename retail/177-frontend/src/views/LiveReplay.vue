<template><DataPage title="直播复盘" description="复盘编号、直播场次、复盘类型、复盘人员、复盘时间和复盘状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'BASE', 'ANCHOR', 'SELECTOR', 'MERCHANT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addLiveReplay, deleteLiveReplay, finishLiveReplay, getLiveReplayPage, processLiveReplay, updateLiveReplay } from '../api'
const api = { page: getLiveReplayPage, add: addLiveReplay, update: updateLiveReplay, delete: deleteLiveReplay, process: processLiveReplay, finish: finishLiveReplay }
const columns = [{ prop: 'recordNo', label: '复盘编号' }, { prop: 'recordName', label: '直播场次' }, { prop: 'category', label: '复盘类型' }, { prop: 'ownerName', label: '复盘人员' }, { prop: 'planTime', label: '复盘时间' }, { prop: 'status', label: '复盘状态' }, { prop: 'remark', label: '复盘说明' }]
const formFields = [{ prop: 'recordNo', label: '复盘编号' }, { prop: 'recordName', label: '直播场次' }, { prop: 'category', label: '复盘类型' }, { prop: 'ownerName', label: '复盘人员' }, { prop: 'planTime', label: '复盘时间' }, { prop: 'status', label: '复盘状态', type: 'select' }, { prop: 'remark', label: '复盘说明', type: 'textarea' }]
</script>

<template><DataPage title="直播间档案" description="直播间编号、直播间名称、直播类型、运营人员、启用时间和直播间状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'BASE', 'ANCHOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addLiveStudio, deleteLiveStudio, finishLiveStudio, getLiveStudioPage, processLiveStudio, updateLiveStudio } from '../api'
const api = { page: getLiveStudioPage, add: addLiveStudio, update: updateLiveStudio, delete: deleteLiveStudio, process: processLiveStudio, finish: finishLiveStudio }
const columns = [{ prop: 'recordNo', label: '直播间编号' }, { prop: 'recordName', label: '直播间名称' }, { prop: 'category', label: '直播类型' }, { prop: 'ownerName', label: '运营人员' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '直播间状态' }, { prop: 'remark', label: '直播间说明' }]
const formFields = [{ prop: 'recordNo', label: '直播间编号' }, { prop: 'recordName', label: '直播间名称' }, { prop: 'category', label: '直播类型' }, { prop: 'ownerName', label: '运营人员' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '直播间状态', type: 'select' }, { prop: 'remark', label: '直播间说明', type: 'textarea' }]
</script>

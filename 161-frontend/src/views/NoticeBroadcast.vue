<template><DataPage title="通知协同" description="通知编号、通知对象、通知类型、发布人、发布时间和通知状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'SERVICE', 'BROADCAST', 'SCENIC']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addNoticeBroadcast, deleteNoticeBroadcast, finishNoticeBroadcast, getNoticeBroadcastPage, processNoticeBroadcast, updateNoticeBroadcast } from '../api'
const api = { page: getNoticeBroadcastPage, add: addNoticeBroadcast, update: updateNoticeBroadcast, delete: deleteNoticeBroadcast, process: processNoticeBroadcast, finish: finishNoticeBroadcast }
const columns = [{ prop: 'recordNo', label: '通知编号' }, { prop: 'recordName', label: '通知对象' }, { prop: 'category', label: '通知类型' }, { prop: 'ownerName', label: '发布人' }, { prop: 'planTime', label: '发布时间' }, { prop: 'status', label: '通知状态' }, { prop: 'remark', label: '通知说明' }]
const formFields = [{ prop: 'recordNo', label: '通知编号' }, { prop: 'recordName', label: '通知对象' }, { prop: 'category', label: '通知类型' }, { prop: 'ownerName', label: '发布人' }, { prop: 'planTime', label: '发布时间' }, { prop: 'status', label: '通知状态', type: 'select' }, { prop: 'remark', label: '通知说明', type: 'textarea' }]
</script>

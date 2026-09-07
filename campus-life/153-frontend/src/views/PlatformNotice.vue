<template><DataPage title="平台公告" description="公告编号、公告标题、公告类型、发布人、发布时间和公告状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'OPERATOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addPlatformNotice, deletePlatformNotice, finishPlatformNotice, getPlatformNoticePage, processPlatformNotice, updatePlatformNotice } from '../api'
const api = { page: getPlatformNoticePage, add: addPlatformNotice, update: updatePlatformNotice, delete: deletePlatformNotice, process: processPlatformNotice, finish: finishPlatformNotice }
const columns = [{ prop: 'recordNo', label: '公告编号' }, { prop: 'recordName', label: '公告标题' }, { prop: 'category', label: '公告类型' }, { prop: 'ownerName', label: '发布人' }, { prop: 'planTime', label: '发布时间' }, { prop: 'status', label: '公告状态' }, { prop: 'remark', label: '公告内容' }]
const formFields = [{ prop: 'recordNo', label: '公告编号' }, { prop: 'recordName', label: '公告标题' }, { prop: 'category', label: '公告类型' }, { prop: 'ownerName', label: '发布人' }, { prop: 'planTime', label: '发布时间' }, { prop: 'status', label: '公告状态', type: 'select' }, { prop: 'remark', label: '公告内容', type: 'textarea' }]
</script>

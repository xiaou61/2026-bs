<template><DataPage title="摊位档案" description="摊位编号、摊位名称、经营类型、摊主姓名、入驻时间和摊位状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'MARKET', 'INSPECTOR', 'VENDOR', 'REGULATOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addStallProfile, deleteStallProfile, finishStallProfile, getStallProfilePage, processStallProfile, updateStallProfile } from '../api'
const api = { page: getStallProfilePage, add: addStallProfile, update: updateStallProfile, delete: deleteStallProfile, process: processStallProfile, finish: finishStallProfile }
const columns = [{ prop: 'recordNo', label: '摊位编号' }, { prop: 'recordName', label: '摊位名称' }, { prop: 'category', label: '经营类型' }, { prop: 'ownerName', label: '摊主姓名' }, { prop: 'planTime', label: '入驻时间' }, { prop: 'status', label: '摊位状态' }, { prop: 'remark', label: '摊位说明' }]
const formFields = [{ prop: 'recordNo', label: '摊位编号' }, { prop: 'recordName', label: '摊位名称' }, { prop: 'category', label: '经营类型' }, { prop: 'ownerName', label: '摊主姓名' }, { prop: 'planTime', label: '入驻时间' }, { prop: 'status', label: '摊位状态', type: 'select' }, { prop: 'remark', label: '摊位说明', type: 'textarea' }]
</script>

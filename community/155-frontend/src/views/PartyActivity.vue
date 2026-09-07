<template><DataPage title="党建活动" description="活动编号、活动主题、活动类型、组织人、活动时间和发布状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'SECRETARY', 'ORGANIZER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addPartyActivity, deletePartyActivity, finishPartyActivity, getPartyActivityPage, processPartyActivity, updatePartyActivity } from '../api'
const api = { page: getPartyActivityPage, add: addPartyActivity, update: updatePartyActivity, delete: deletePartyActivity, process: processPartyActivity, finish: finishPartyActivity }
const columns = [{ prop: 'recordNo', label: '活动编号' }, { prop: 'recordName', label: '活动主题' }, { prop: 'category', label: '活动类型' }, { prop: 'ownerName', label: '组织人' }, { prop: 'planTime', label: '活动时间' }, { prop: 'status', label: '发布状态' }, { prop: 'remark', label: '活动说明' }]
const formFields = [{ prop: 'recordNo', label: '活动编号' }, { prop: 'recordName', label: '活动主题' }, { prop: 'category', label: '活动类型' }, { prop: 'ownerName', label: '组织人' }, { prop: 'planTime', label: '活动时间' }, { prop: 'status', label: '发布状态', type: 'select' }, { prop: 'remark', label: '活动说明', type: 'textarea' }]
</script>

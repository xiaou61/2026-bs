<template><DataPage title="阅读活动" description="活动编号、活动名称、活动类型、组织人员、活动时间和活动状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'LIBRARY', 'STUDENT', 'CLUB', 'TEACHER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addReadingActivity, deleteReadingActivity, finishReadingActivity, getReadingActivityPage, processReadingActivity, updateReadingActivity } from '../api'
const api = { page: getReadingActivityPage, add: addReadingActivity, update: updateReadingActivity, delete: deleteReadingActivity, process: processReadingActivity, finish: finishReadingActivity }
const columns = [{ prop: 'recordNo', label: '活动编号' }, { prop: 'recordName', label: '活动名称' }, { prop: 'category', label: '活动类型' }, { prop: 'ownerName', label: '组织人员' }, { prop: 'planTime', label: '活动时间' }, { prop: 'status', label: '活动状态' }, { prop: 'remark', label: '活动说明' }]
const formFields = [{ prop: 'recordNo', label: '活动编号' }, { prop: 'recordName', label: '活动名称' }, { prop: 'category', label: '活动类型' }, { prop: 'ownerName', label: '组织人员' }, { prop: 'planTime', label: '活动时间' }, { prop: 'status', label: '活动状态', type: 'select' }, { prop: 'remark', label: '活动说明', type: 'textarea' }]
</script>

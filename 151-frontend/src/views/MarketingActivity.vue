<template><DataPage title="文旅活动" description="活动编号、活动名称、活动类型、策划人、活动时间和发布状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'MANAGER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addMarketingActivity, deleteMarketingActivity, finishMarketingActivity, getMarketingActivityPage, processMarketingActivity, updateMarketingActivity } from '../api'
const api = { page: getMarketingActivityPage, add: addMarketingActivity, update: updateMarketingActivity, delete: deleteMarketingActivity, process: processMarketingActivity, finish: finishMarketingActivity }
const columns = [{ prop: 'recordNo', label: '活动编号' }, { prop: 'recordName', label: '活动名称' }, { prop: 'category', label: '活动类型' }, { prop: 'ownerName', label: '策划人' }, { prop: 'planTime', label: '活动时间' }, { prop: 'status', label: '发布状态' }, { prop: 'remark', label: '活动说明' }]
const formFields = [{ prop: 'recordNo', label: '活动编号' }, { prop: 'recordName', label: '活动名称' }, { prop: 'category', label: '活动类型' }, { prop: 'ownerName', label: '策划人' }, { prop: 'planTime', label: '活动时间' }, { prop: 'status', label: '发布状态', type: 'select' }, { prop: 'remark', label: '活动说明', type: 'textarea' }]
</script>

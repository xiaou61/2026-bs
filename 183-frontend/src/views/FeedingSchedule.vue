<template><DataPage title="饲养排班" description="排班编号、动物房间、排班类型、饲养人员、排班时间和排班状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'LAB', 'BREEDER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addFeedingSchedule, deleteFeedingSchedule, finishFeedingSchedule, getFeedingSchedulePage, processFeedingSchedule, updateFeedingSchedule } from '../api'
const api = { page: getFeedingSchedulePage, add: addFeedingSchedule, update: updateFeedingSchedule, delete: deleteFeedingSchedule, process: processFeedingSchedule, finish: finishFeedingSchedule }
const columns = [{ prop: 'recordNo', label: '排班编号' }, { prop: 'recordName', label: '动物房间' }, { prop: 'category', label: '排班类型' }, { prop: 'ownerName', label: '饲养人员' }, { prop: 'planTime', label: '排班时间' }, { prop: 'status', label: '排班状态' }, { prop: 'remark', label: '排班说明' }]
const formFields = [{ prop: 'recordNo', label: '排班编号' }, { prop: 'recordName', label: '动物房间' }, { prop: 'category', label: '排班类型' }, { prop: 'ownerName', label: '饲养人员' }, { prop: 'planTime', label: '排班时间' }, { prop: 'status', label: '排班状态', type: 'select' }, { prop: 'remark', label: '排班说明', type: 'textarea' }]
</script>

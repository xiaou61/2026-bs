<template><DataPage title="讲解预约" description="讲解预约单、讲解主题、团队类型、联系人、预约时间和确认状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'MANAGER', 'GUIDE', 'VISITOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addGuideBooking, deleteGuideBooking, finishGuideBooking, getGuideBookingPage, processGuideBooking, updateGuideBooking } from '../api'
const api = { page: getGuideBookingPage, add: addGuideBooking, update: updateGuideBooking, delete: deleteGuideBooking, process: processGuideBooking, finish: finishGuideBooking }
const columns = [{ prop: 'recordNo', label: '讲解预约单' }, { prop: 'recordName', label: '讲解主题' }, { prop: 'category', label: '团队类型' }, { prop: 'ownerName', label: '联系人' }, { prop: 'planTime', label: '预约时间' }, { prop: 'status', label: '确认状态' }, { prop: 'remark', label: '讲解需求' }]
const formFields = [{ prop: 'recordNo', label: '讲解预约单' }, { prop: 'recordName', label: '讲解主题' }, { prop: 'category', label: '团队类型' }, { prop: 'ownerName', label: '联系人' }, { prop: 'planTime', label: '预约时间' }, { prop: 'status', label: '确认状态', type: 'select' }, { prop: 'remark', label: '讲解需求', type: 'textarea' }]
</script>

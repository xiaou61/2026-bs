<template><DataPage title="服务预约" description="预约编号、居民姓名、服务类型、申请人员、预约时间和预约状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'AGENCY', 'DISPATCH', 'RESIDENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addServiceBooking, deleteServiceBooking, finishServiceBooking, getServiceBookingPage, processServiceBooking, updateServiceBooking } from '../api'
const api = { page: getServiceBookingPage, add: addServiceBooking, update: updateServiceBooking, delete: deleteServiceBooking, process: processServiceBooking, finish: finishServiceBooking }
const columns = [{ prop: 'recordNo', label: '预约编号' }, { prop: 'recordName', label: '居民姓名' }, { prop: 'category', label: '服务类型' }, { prop: 'ownerName', label: '申请人员' }, { prop: 'planTime', label: '预约时间' }, { prop: 'status', label: '预约状态' }, { prop: 'remark', label: '预约说明' }]
const formFields = [{ prop: 'recordNo', label: '预约编号' }, { prop: 'recordName', label: '居民姓名' }, { prop: 'category', label: '服务类型' }, { prop: 'ownerName', label: '申请人员' }, { prop: 'planTime', label: '预约时间' }, { prop: 'status', label: '预约状态', type: 'select' }, { prop: 'remark', label: '预约说明', type: 'textarea' }]
</script>

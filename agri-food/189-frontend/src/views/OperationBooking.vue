<template><DataPage title="作业预约" description="预约编号、作业地块、作业类型、预约农户、预约时间和预约状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'STATION', 'DISPATCH', 'FARMER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addOperationBooking, deleteOperationBooking, finishOperationBooking, getOperationBookingPage, processOperationBooking, updateOperationBooking } from '../api'
const api = { page: getOperationBookingPage, add: addOperationBooking, update: updateOperationBooking, delete: deleteOperationBooking, process: processOperationBooking, finish: finishOperationBooking }
const columns = [{ prop: 'recordNo', label: '预约编号' }, { prop: 'recordName', label: '作业地块' }, { prop: 'category', label: '作业类型' }, { prop: 'ownerName', label: '预约农户' }, { prop: 'planTime', label: '预约时间' }, { prop: 'status', label: '预约状态' }, { prop: 'remark', label: '预约说明' }]
const formFields = [{ prop: 'recordNo', label: '预约编号' }, { prop: 'recordName', label: '作业地块' }, { prop: 'category', label: '作业类型' }, { prop: 'ownerName', label: '预约农户' }, { prop: 'planTime', label: '预约时间' }, { prop: 'status', label: '预约状态', type: 'select' }, { prop: 'remark', label: '预约说明', type: 'textarea' }]
</script>

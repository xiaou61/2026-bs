<template><DataPage title="乘车预约" description="预约编号、预约线路、预约类型、预约学生、预约时间和预约状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'SCHOOL', 'DISPATCH', 'TEACHER', 'GUARDIAN']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addRideReservation, deleteRideReservation, finishRideReservation, getRideReservationPage, processRideReservation, updateRideReservation } from '../api'
const api = { page: getRideReservationPage, add: addRideReservation, update: updateRideReservation, delete: deleteRideReservation, process: processRideReservation, finish: finishRideReservation }
const columns = [{ prop: 'recordNo', label: '预约编号' }, { prop: 'recordName', label: '预约线路' }, { prop: 'category', label: '预约类型' }, { prop: 'ownerName', label: '预约学生' }, { prop: 'planTime', label: '预约时间' }, { prop: 'status', label: '预约状态' }, { prop: 'remark', label: '预约说明' }]
const formFields = [{ prop: 'recordNo', label: '预约编号' }, { prop: 'recordName', label: '预约线路' }, { prop: 'category', label: '预约类型' }, { prop: 'ownerName', label: '预约学生' }, { prop: 'planTime', label: '预约时间' }, { prop: 'status', label: '预约状态', type: 'select' }, { prop: 'remark', label: '预约说明', type: 'textarea' }]
</script>

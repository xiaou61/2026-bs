<template><DataPage title="接诊预约" description="预约编号、宠物名称、接诊类型、预约人、预约时间和预约状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'RECEPTION', 'DOCTOR', 'OWNER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addVisitAppointment, deleteVisitAppointment, finishVisitAppointment, getVisitAppointmentPage, processVisitAppointment, updateVisitAppointment } from '../api'
const api = { page: getVisitAppointmentPage, add: addVisitAppointment, update: updateVisitAppointment, delete: deleteVisitAppointment, process: processVisitAppointment, finish: finishVisitAppointment }
const columns = [{ prop: 'recordNo', label: '预约编号' }, { prop: 'recordName', label: '宠物名称' }, { prop: 'category', label: '接诊类型' }, { prop: 'ownerName', label: '预约人' }, { prop: 'planTime', label: '预约时间' }, { prop: 'status', label: '预约状态' }, { prop: 'remark', label: '症状描述' }]
const formFields = [{ prop: 'recordNo', label: '预约编号' }, { prop: 'recordName', label: '宠物名称' }, { prop: 'category', label: '接诊类型' }, { prop: 'ownerName', label: '预约人' }, { prop: 'planTime', label: '预约时间' }, { prop: 'status', label: '预约状态', type: 'select' }, { prop: 'remark', label: '症状描述', type: 'textarea' }]
</script>

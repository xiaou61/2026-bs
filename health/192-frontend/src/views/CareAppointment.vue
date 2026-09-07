<template><DataPage title="陪护预约" description="预约编号、服务患者、陪护类型、申请人员、预约时间和预约状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'HOSPITAL', 'COORDINATOR', 'FAMILY']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addCareAppointment, deleteCareAppointment, finishCareAppointment, getCareAppointmentPage, processCareAppointment, updateCareAppointment } from '../api'
const api = { page: getCareAppointmentPage, add: addCareAppointment, update: updateCareAppointment, delete: deleteCareAppointment, process: processCareAppointment, finish: finishCareAppointment }
const columns = [{ prop: 'recordNo', label: '预约编号' }, { prop: 'recordName', label: '服务患者' }, { prop: 'category', label: '陪护类型' }, { prop: 'ownerName', label: '申请人员' }, { prop: 'planTime', label: '预约时间' }, { prop: 'status', label: '预约状态' }, { prop: 'remark', label: '预约说明' }]
const formFields = [{ prop: 'recordNo', label: '预约编号' }, { prop: 'recordName', label: '服务患者' }, { prop: 'category', label: '陪护类型' }, { prop: 'ownerName', label: '申请人员' }, { prop: 'planTime', label: '预约时间' }, { prop: 'status', label: '预约状态', type: 'select' }, { prop: 'remark', label: '预约说明', type: 'textarea' }]
</script>

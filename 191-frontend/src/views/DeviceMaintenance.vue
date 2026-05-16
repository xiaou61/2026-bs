<template><DataPage title="器具维护" description="维护编号、维护器具、维护类型、维护人员、维护时间和维护状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'COMMUNITY', 'AIDSTAFF']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addDeviceMaintenance, deleteDeviceMaintenance, finishDeviceMaintenance, getDeviceMaintenancePage, processDeviceMaintenance, updateDeviceMaintenance } from '../api'
const api = { page: getDeviceMaintenancePage, add: addDeviceMaintenance, update: updateDeviceMaintenance, delete: deleteDeviceMaintenance, process: processDeviceMaintenance, finish: finishDeviceMaintenance }
const columns = [{ prop: 'recordNo', label: '维护编号' }, { prop: 'recordName', label: '维护器具' }, { prop: 'category', label: '维护类型' }, { prop: 'ownerName', label: '维护人员' }, { prop: 'planTime', label: '维护时间' }, { prop: 'status', label: '维护状态' }, { prop: 'remark', label: '维护说明' }]
const formFields = [{ prop: 'recordNo', label: '维护编号' }, { prop: 'recordName', label: '维护器具' }, { prop: 'category', label: '维护类型' }, { prop: 'ownerName', label: '维护人员' }, { prop: 'planTime', label: '维护时间' }, { prop: 'status', label: '维护状态', type: 'select' }, { prop: 'remark', label: '维护说明', type: 'textarea' }]
</script>

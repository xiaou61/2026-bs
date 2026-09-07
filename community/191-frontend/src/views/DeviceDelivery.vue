<template><DataPage title="器具交付" description="交付编号、借用申请、交付方式、交付人员、交付时间和交付状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'COMMUNITY', 'AIDSTAFF', 'VOLUNTEER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addDeviceDelivery, deleteDeviceDelivery, finishDeviceDelivery, getDeviceDeliveryPage, processDeviceDelivery, updateDeviceDelivery } from '../api'
const api = { page: getDeviceDeliveryPage, add: addDeviceDelivery, update: updateDeviceDelivery, delete: deleteDeviceDelivery, process: processDeviceDelivery, finish: finishDeviceDelivery }
const columns = [{ prop: 'recordNo', label: '交付编号' }, { prop: 'recordName', label: '借用申请' }, { prop: 'category', label: '交付方式' }, { prop: 'ownerName', label: '交付人员' }, { prop: 'planTime', label: '交付时间' }, { prop: 'status', label: '交付状态' }, { prop: 'remark', label: '交付说明' }]
const formFields = [{ prop: 'recordNo', label: '交付编号' }, { prop: 'recordName', label: '借用申请' }, { prop: 'category', label: '交付方式' }, { prop: 'ownerName', label: '交付人员' }, { prop: 'planTime', label: '交付时间' }, { prop: 'status', label: '交付状态', type: 'select' }, { prop: 'remark', label: '交付说明', type: 'textarea' }]
</script>

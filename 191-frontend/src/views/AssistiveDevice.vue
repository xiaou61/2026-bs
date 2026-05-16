<template><DataPage title="助残器具" description="器具编号、器具名称、器具类型、保管人员、入库时间和器具状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'COMMUNITY', 'AIDSTAFF']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addAssistiveDevice, deleteAssistiveDevice, finishAssistiveDevice, getAssistiveDevicePage, processAssistiveDevice, updateAssistiveDevice } from '../api'
const api = { page: getAssistiveDevicePage, add: addAssistiveDevice, update: updateAssistiveDevice, delete: deleteAssistiveDevice, process: processAssistiveDevice, finish: finishAssistiveDevice }
const columns = [{ prop: 'recordNo', label: '器具编号' }, { prop: 'recordName', label: '器具名称' }, { prop: 'category', label: '器具类型' }, { prop: 'ownerName', label: '保管人员' }, { prop: 'planTime', label: '入库时间' }, { prop: 'status', label: '器具状态' }, { prop: 'remark', label: '器具说明' }]
const formFields = [{ prop: 'recordNo', label: '器具编号' }, { prop: 'recordName', label: '器具名称' }, { prop: 'category', label: '器具类型' }, { prop: 'ownerName', label: '保管人员' }, { prop: 'planTime', label: '入库时间' }, { prop: 'status', label: '器具状态', type: 'select' }, { prop: 'remark', label: '器具说明', type: 'textarea' }]
</script>

<template><DataPage title="故障维修" description="维修编号、故障设备、故障类型、维修人员、报修时间和维修状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'OPERATOR', 'INSPECTOR', 'SITE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addFaultRepair, deleteFaultRepair, finishFaultRepair, getFaultRepairPage, processFaultRepair, updateFaultRepair } from '../api'
const api = { page: getFaultRepairPage, add: addFaultRepair, update: updateFaultRepair, delete: deleteFaultRepair, process: processFaultRepair, finish: finishFaultRepair }
const columns = [{ prop: 'recordNo', label: '维修编号' }, { prop: 'recordName', label: '故障设备' }, { prop: 'category', label: '故障类型' }, { prop: 'ownerName', label: '维修人员' }, { prop: 'planTime', label: '报修时间' }, { prop: 'status', label: '维修状态' }, { prop: 'remark', label: '维修说明' }]
const formFields = [{ prop: 'recordNo', label: '维修编号' }, { prop: 'recordName', label: '故障设备' }, { prop: 'category', label: '故障类型' }, { prop: 'ownerName', label: '维修人员' }, { prop: 'planTime', label: '报修时间' }, { prop: 'status', label: '维修状态', type: 'select' }, { prop: 'remark', label: '维修说明', type: 'textarea' }]
</script>

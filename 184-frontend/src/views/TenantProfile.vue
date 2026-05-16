<template><DataPage title="月租车主" description="车主编号、车主姓名、客户类型、联系人、建档时间和车主状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'PARKING', 'TENANT', 'SERVICE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addTenantProfile, deleteTenantProfile, finishTenantProfile, getTenantProfilePage, processTenantProfile, updateTenantProfile } from '../api'
const api = { page: getTenantProfilePage, add: addTenantProfile, update: updateTenantProfile, delete: deleteTenantProfile, process: processTenantProfile, finish: finishTenantProfile }
const columns = [{ prop: 'recordNo', label: '车主编号' }, { prop: 'recordName', label: '车主姓名' }, { prop: 'category', label: '客户类型' }, { prop: 'ownerName', label: '联系人' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '车主状态' }, { prop: 'remark', label: '车主说明' }]
const formFields = [{ prop: 'recordNo', label: '车主编号' }, { prop: 'recordName', label: '车主姓名' }, { prop: 'category', label: '客户类型' }, { prop: 'ownerName', label: '联系人' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '车主状态', type: 'select' }, { prop: 'remark', label: '车主说明', type: 'textarea' }]
</script>

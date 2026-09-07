<template><DataPage title="入驻档案" description="档案编号、入驻单位、租户类型、联系人、入驻时间和服务状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'PROPERTY', 'DISPATCH', 'TENANT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addTenantProfile, deleteTenantProfile, finishTenantProfile, getTenantProfilePage, processTenantProfile, updateTenantProfile } from '../api'
const api = { page: getTenantProfilePage, add: addTenantProfile, update: updateTenantProfile, delete: deleteTenantProfile, process: processTenantProfile, finish: finishTenantProfile }
const columns = [{ prop: 'recordNo', label: '档案编号' }, { prop: 'recordName', label: '入驻单位' }, { prop: 'category', label: '租户类型' }, { prop: 'ownerName', label: '联系人' }, { prop: 'planTime', label: '入驻时间' }, { prop: 'status', label: '服务状态' }, { prop: 'remark', label: '档案说明' }]
const formFields = [{ prop: 'recordNo', label: '档案编号' }, { prop: 'recordName', label: '入驻单位' }, { prop: 'category', label: '租户类型' }, { prop: 'ownerName', label: '联系人' }, { prop: 'planTime', label: '入驻时间' }, { prop: 'status', label: '服务状态', type: 'select' }, { prop: 'remark', label: '档案说明', type: 'textarea' }]
</script>

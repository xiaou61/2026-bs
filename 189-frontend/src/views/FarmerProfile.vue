<template><DataPage title="农户档案" description="农户编号、农户姓名、种植类型、联系人、建档时间和服务状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'STATION', 'DISPATCH', 'FARMER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addFarmerProfile, deleteFarmerProfile, finishFarmerProfile, getFarmerProfilePage, processFarmerProfile, updateFarmerProfile } from '../api'
const api = { page: getFarmerProfilePage, add: addFarmerProfile, update: updateFarmerProfile, delete: deleteFarmerProfile, process: processFarmerProfile, finish: finishFarmerProfile }
const columns = [{ prop: 'recordNo', label: '农户编号' }, { prop: 'recordName', label: '农户姓名' }, { prop: 'category', label: '种植类型' }, { prop: 'ownerName', label: '联系人' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '服务状态' }, { prop: 'remark', label: '农户说明' }]
const formFields = [{ prop: 'recordNo', label: '农户编号' }, { prop: 'recordName', label: '农户姓名' }, { prop: 'category', label: '种植类型' }, { prop: 'ownerName', label: '联系人' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '服务状态', type: 'select' }, { prop: 'remark', label: '农户说明', type: 'textarea' }]
</script>

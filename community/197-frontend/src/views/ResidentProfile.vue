<template><DataPage title="居民档案" description="档案编号、居民姓名、服务需求、联系人员、建档时间和档案状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'AGENCY', 'DISPATCH', 'RESIDENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addResidentProfile, deleteResidentProfile, finishResidentProfile, getResidentProfilePage, processResidentProfile, updateResidentProfile } from '../api'
const api = { page: getResidentProfilePage, add: addResidentProfile, update: updateResidentProfile, delete: deleteResidentProfile, process: processResidentProfile, finish: finishResidentProfile }
const columns = [{ prop: 'recordNo', label: '档案编号' }, { prop: 'recordName', label: '居民姓名' }, { prop: 'category', label: '服务需求' }, { prop: 'ownerName', label: '联系人员' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '档案状态' }, { prop: 'remark', label: '档案说明' }]
const formFields = [{ prop: 'recordNo', label: '档案编号' }, { prop: 'recordName', label: '居民姓名' }, { prop: 'category', label: '服务需求' }, { prop: 'ownerName', label: '联系人员' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '档案状态', type: 'select' }, { prop: 'remark', label: '档案说明', type: 'textarea' }]
</script>

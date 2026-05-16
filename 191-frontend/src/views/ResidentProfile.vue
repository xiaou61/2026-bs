<template><DataPage title="居民档案" description="档案编号、居民姓名、需求类别、联系人、登记时间和服务状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'COMMUNITY', 'THERAPIST', 'VOLUNTEER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addResidentProfile, deleteResidentProfile, finishResidentProfile, getResidentProfilePage, processResidentProfile, updateResidentProfile } from '../api'
const api = { page: getResidentProfilePage, add: addResidentProfile, update: updateResidentProfile, delete: deleteResidentProfile, process: processResidentProfile, finish: finishResidentProfile }
const columns = [{ prop: 'recordNo', label: '档案编号' }, { prop: 'recordName', label: '居民姓名' }, { prop: 'category', label: '需求类别' }, { prop: 'ownerName', label: '联系人' }, { prop: 'planTime', label: '登记时间' }, { prop: 'status', label: '服务状态' }, { prop: 'remark', label: '档案说明' }]
const formFields = [{ prop: 'recordNo', label: '档案编号' }, { prop: 'recordName', label: '居民姓名' }, { prop: 'category', label: '需求类别' }, { prop: 'ownerName', label: '联系人' }, { prop: 'planTime', label: '登记时间' }, { prop: 'status', label: '服务状态', type: 'select' }, { prop: 'remark', label: '档案说明', type: 'textarea' }]
</script>

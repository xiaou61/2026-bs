<template><DataPage title="机手档案" description="机手编号、机手姓名、作业类型、所属站点、建档时间和机手状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'STATION', 'DISPATCH', 'DRIVER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addDriverProfile, deleteDriverProfile, finishDriverProfile, getDriverProfilePage, processDriverProfile, updateDriverProfile } from '../api'
const api = { page: getDriverProfilePage, add: addDriverProfile, update: updateDriverProfile, delete: deleteDriverProfile, process: processDriverProfile, finish: finishDriverProfile }
const columns = [{ prop: 'recordNo', label: '机手编号' }, { prop: 'recordName', label: '机手姓名' }, { prop: 'category', label: '作业类型' }, { prop: 'ownerName', label: '所属站点' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '机手状态' }, { prop: 'remark', label: '机手说明' }]
const formFields = [{ prop: 'recordNo', label: '机手编号' }, { prop: 'recordName', label: '机手姓名' }, { prop: 'category', label: '作业类型' }, { prop: 'ownerName', label: '所属站点' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '机手状态', type: 'select' }, { prop: 'remark', label: '机手说明', type: 'textarea' }]
</script>

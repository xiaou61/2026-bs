<template><DataPage title="司机档案" description="司机编号、司机姓名、准驾类型、管理人员、入职时间和司机状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'SCHOOL', 'DISPATCH', 'DRIVER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addDriverProfile, deleteDriverProfile, finishDriverProfile, getDriverProfilePage, processDriverProfile, updateDriverProfile } from '../api'
const api = { page: getDriverProfilePage, add: addDriverProfile, update: updateDriverProfile, delete: deleteDriverProfile, process: processDriverProfile, finish: finishDriverProfile }
const columns = [{ prop: 'recordNo', label: '司机编号' }, { prop: 'recordName', label: '司机姓名' }, { prop: 'category', label: '准驾类型' }, { prop: 'ownerName', label: '管理人员' }, { prop: 'planTime', label: '入职时间' }, { prop: 'status', label: '司机状态' }, { prop: 'remark', label: '司机说明' }]
const formFields = [{ prop: 'recordNo', label: '司机编号' }, { prop: 'recordName', label: '司机姓名' }, { prop: 'category', label: '准驾类型' }, { prop: 'ownerName', label: '管理人员' }, { prop: 'planTime', label: '入职时间' }, { prop: 'status', label: '司机状态', type: 'select' }, { prop: 'remark', label: '司机说明', type: 'textarea' }]
</script>

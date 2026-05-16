<template><DataPage title="暂存巡检" description="巡检编号、暂存设施、巡检类型、巡检人员、巡检时间和巡检状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'PARK', 'WAREHOUSE', 'INSPECTOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addStorageCheck, deleteStorageCheck, finishStorageCheck, getStorageCheckPage, processStorageCheck, updateStorageCheck } from '../api'
const api = { page: getStorageCheckPage, add: addStorageCheck, update: updateStorageCheck, delete: deleteStorageCheck, process: processStorageCheck, finish: finishStorageCheck }
const columns = [{ prop: 'recordNo', label: '巡检编号' }, { prop: 'recordName', label: '暂存设施' }, { prop: 'category', label: '巡检类型' }, { prop: 'ownerName', label: '巡检人员' }, { prop: 'planTime', label: '巡检时间' }, { prop: 'status', label: '巡检状态' }, { prop: 'remark', label: '巡检说明' }]
const formFields = [{ prop: 'recordNo', label: '巡检编号' }, { prop: 'recordName', label: '暂存设施' }, { prop: 'category', label: '巡检类型' }, { prop: 'ownerName', label: '巡检人员' }, { prop: 'planTime', label: '巡检时间' }, { prop: 'status', label: '巡检状态', type: 'select' }, { prop: 'remark', label: '巡检说明', type: 'textarea' }]
</script>

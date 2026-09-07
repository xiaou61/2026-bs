<template><DataPage title="工坊档案" description="工坊编号、工坊名称、非遗门类、负责人、建档时间和工坊状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'WORKSHOP']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addWorkshopProfile, deleteWorkshopProfile, finishWorkshopProfile, getWorkshopProfilePage, processWorkshopProfile, updateWorkshopProfile } from '../api'
const api = { page: getWorkshopProfilePage, add: addWorkshopProfile, update: updateWorkshopProfile, delete: deleteWorkshopProfile, process: processWorkshopProfile, finish: finishWorkshopProfile }
const columns = [{ prop: 'recordNo', label: '工坊编号' }, { prop: 'recordName', label: '工坊名称' }, { prop: 'category', label: '非遗门类' }, { prop: 'ownerName', label: '负责人' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '工坊状态' }, { prop: 'remark', label: '工坊说明' }]
const formFields = [{ prop: 'recordNo', label: '工坊编号' }, { prop: 'recordName', label: '工坊名称' }, { prop: 'category', label: '非遗门类' }, { prop: 'ownerName', label: '负责人' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '工坊状态', type: 'select' }, { prop: 'remark', label: '工坊说明', type: 'textarea' }]
</script>

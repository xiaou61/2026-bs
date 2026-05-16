<template><DataPage title="车位档案" description="车位编号、车位区域、车位类型、管理人员、启用时间和车位状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'PARKING', 'PATROL']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addParkingSpace, deleteParkingSpace, finishParkingSpace, getParkingSpacePage, processParkingSpace, updateParkingSpace } from '../api'
const api = { page: getParkingSpacePage, add: addParkingSpace, update: updateParkingSpace, delete: deleteParkingSpace, process: processParkingSpace, finish: finishParkingSpace }
const columns = [{ prop: 'recordNo', label: '车位编号' }, { prop: 'recordName', label: '车位区域' }, { prop: 'category', label: '车位类型' }, { prop: 'ownerName', label: '管理人员' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '车位状态' }, { prop: 'remark', label: '车位说明' }]
const formFields = [{ prop: 'recordNo', label: '车位编号' }, { prop: 'recordName', label: '车位区域' }, { prop: 'category', label: '车位类型' }, { prop: 'ownerName', label: '管理人员' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '车位状态', type: 'select' }, { prop: 'remark', label: '车位说明', type: 'textarea' }]
</script>

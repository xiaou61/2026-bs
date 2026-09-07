<template><DataPage title="车辆档案" description="车辆编号、车牌号码、车辆类型、司机姓名、备案时间和车辆状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'DISPATCHER', 'GATEKEEPER', 'CARRIER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addVehicleProfile, deleteVehicleProfile, finishVehicleProfile, getVehicleProfilePage, processVehicleProfile, updateVehicleProfile } from '../api'
const api = { page: getVehicleProfilePage, add: addVehicleProfile, update: updateVehicleProfile, delete: deleteVehicleProfile, process: processVehicleProfile, finish: finishVehicleProfile }
const columns = [{ prop: 'recordNo', label: '车辆编号' }, { prop: 'recordName', label: '车牌号码' }, { prop: 'category', label: '车辆类型' }, { prop: 'ownerName', label: '司机姓名' }, { prop: 'planTime', label: '备案时间' }, { prop: 'status', label: '车辆状态' }, { prop: 'remark', label: '车辆说明' }]
const formFields = [{ prop: 'recordNo', label: '车辆编号' }, { prop: 'recordName', label: '车牌号码' }, { prop: 'category', label: '车辆类型' }, { prop: 'ownerName', label: '司机姓名' }, { prop: 'planTime', label: '备案时间' }, { prop: 'status', label: '车辆状态', type: 'select' }, { prop: 'remark', label: '车辆说明', type: 'textarea' }]
</script>

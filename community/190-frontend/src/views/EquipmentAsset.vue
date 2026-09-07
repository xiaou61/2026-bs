<template><DataPage title="设备档案" description="设备编号、设备名称、设备类型、管护人员、启用时间和设备状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'PROPERTY', 'TECHNICIAN', 'INSPECTOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addEquipmentAsset, deleteEquipmentAsset, finishEquipmentAsset, getEquipmentAssetPage, processEquipmentAsset, updateEquipmentAsset } from '../api'
const api = { page: getEquipmentAssetPage, add: addEquipmentAsset, update: updateEquipmentAsset, delete: deleteEquipmentAsset, process: processEquipmentAsset, finish: finishEquipmentAsset }
const columns = [{ prop: 'recordNo', label: '设备编号' }, { prop: 'recordName', label: '设备名称' }, { prop: 'category', label: '设备类型' }, { prop: 'ownerName', label: '管护人员' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '设备状态' }, { prop: 'remark', label: '设备说明' }]
const formFields = [{ prop: 'recordNo', label: '设备编号' }, { prop: 'recordName', label: '设备名称' }, { prop: 'category', label: '设备类型' }, { prop: 'ownerName', label: '管护人员' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '设备状态', type: 'select' }, { prop: 'remark', label: '设备说明', type: 'textarea' }]
</script>

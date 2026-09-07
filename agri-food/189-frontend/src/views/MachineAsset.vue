<template><DataPage title="农机档案" description="农机编号、农机名称、农机类型、管理人员、入库时间和农机状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'STATION', 'MECHANIC']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addMachineAsset, deleteMachineAsset, finishMachineAsset, getMachineAssetPage, processMachineAsset, updateMachineAsset } from '../api'
const api = { page: getMachineAssetPage, add: addMachineAsset, update: updateMachineAsset, delete: deleteMachineAsset, process: processMachineAsset, finish: finishMachineAsset }
const columns = [{ prop: 'recordNo', label: '农机编号' }, { prop: 'recordName', label: '农机名称' }, { prop: 'category', label: '农机类型' }, { prop: 'ownerName', label: '管理人员' }, { prop: 'planTime', label: '入库时间' }, { prop: 'status', label: '农机状态' }, { prop: 'remark', label: '农机说明' }]
const formFields = [{ prop: 'recordNo', label: '农机编号' }, { prop: 'recordName', label: '农机名称' }, { prop: 'category', label: '农机类型' }, { prop: 'ownerName', label: '管理人员' }, { prop: 'planTime', label: '入库时间' }, { prop: 'status', label: '农机状态', type: 'select' }, { prop: 'remark', label: '农机说明', type: 'textarea' }]
</script>

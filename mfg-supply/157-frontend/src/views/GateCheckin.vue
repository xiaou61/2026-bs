<template><DataPage title="门岗核验" description="核验编号、入场车辆、核验类型、核验人、核验时间和核验状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'DISPATCHER', 'GATEKEEPER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addGateCheckin, deleteGateCheckin, finishGateCheckin, getGateCheckinPage, processGateCheckin, updateGateCheckin } from '../api'
const api = { page: getGateCheckinPage, add: addGateCheckin, update: updateGateCheckin, delete: deleteGateCheckin, process: processGateCheckin, finish: finishGateCheckin }
const columns = [{ prop: 'recordNo', label: '核验编号' }, { prop: 'recordName', label: '入场车辆' }, { prop: 'category', label: '核验类型' }, { prop: 'ownerName', label: '核验人' }, { prop: 'planTime', label: '核验时间' }, { prop: 'status', label: '核验状态' }, { prop: 'remark', label: '核验说明' }]
const formFields = [{ prop: 'recordNo', label: '核验编号' }, { prop: 'recordName', label: '入场车辆' }, { prop: 'category', label: '核验类型' }, { prop: 'ownerName', label: '核验人' }, { prop: 'planTime', label: '核验时间' }, { prop: 'status', label: '核验状态', type: 'select' }, { prop: 'remark', label: '核验说明', type: 'textarea' }]
</script>

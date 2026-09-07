<template><DataPage title="手术室档案" description="手术室编号、手术室名称、手术类型、责任护士、启用时间和手术室状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'ORNURSE', 'SURGEON']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addOperatingRoom, deleteOperatingRoom, finishOperatingRoom, getOperatingRoomPage, processOperatingRoom, updateOperatingRoom } from '../api'
const api = { page: getOperatingRoomPage, add: addOperatingRoom, update: updateOperatingRoom, delete: deleteOperatingRoom, process: processOperatingRoom, finish: finishOperatingRoom }
const columns = [{ prop: 'recordNo', label: '手术室编号' }, { prop: 'recordName', label: '手术室名称' }, { prop: 'category', label: '手术类型' }, { prop: 'ownerName', label: '责任护士' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '手术室状态' }, { prop: 'remark', label: '手术室说明' }]
const formFields = [{ prop: 'recordNo', label: '手术室编号' }, { prop: 'recordName', label: '手术室名称' }, { prop: 'category', label: '手术类型' }, { prop: 'ownerName', label: '责任护士' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '手术室状态', type: 'select' }, { prop: 'remark', label: '手术室说明', type: 'textarea' }]
</script>

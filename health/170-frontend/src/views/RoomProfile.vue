<template><DataPage title="房间档案" description="房间编号、房间名称、房间类型、负责人员、启用时间和房间状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'ADMISSION', 'NURSING', 'DIRECTOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addRoomProfile, deleteRoomProfile, finishRoomProfile, getRoomProfilePage, processRoomProfile, updateRoomProfile } from '../api'
const api = { page: getRoomProfilePage, add: addRoomProfile, update: updateRoomProfile, delete: deleteRoomProfile, process: processRoomProfile, finish: finishRoomProfile }
const columns = [{ prop: 'recordNo', label: '房间编号' }, { prop: 'recordName', label: '房间名称' }, { prop: 'category', label: '房间类型' }, { prop: 'ownerName', label: '负责人员' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '房间状态' }, { prop: 'remark', label: '房间说明' }]
const formFields = [{ prop: 'recordNo', label: '房间编号' }, { prop: 'recordName', label: '房间名称' }, { prop: 'category', label: '房间类型' }, { prop: 'ownerName', label: '负责人员' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '房间状态', type: 'select' }, { prop: 'remark', label: '房间说明', type: 'textarea' }]
</script>

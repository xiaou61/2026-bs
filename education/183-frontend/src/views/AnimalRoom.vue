<template><DataPage title="动物房间" description="房间编号、房间名称、屏障级别、负责人员、启用时间和房间状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'LAB', 'BREEDER', 'VET']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addAnimalRoom, deleteAnimalRoom, finishAnimalRoom, getAnimalRoomPage, processAnimalRoom, updateAnimalRoom } from '../api'
const api = { page: getAnimalRoomPage, add: addAnimalRoom, update: updateAnimalRoom, delete: deleteAnimalRoom, process: processAnimalRoom, finish: finishAnimalRoom }
const columns = [{ prop: 'recordNo', label: '房间编号' }, { prop: 'recordName', label: '房间名称' }, { prop: 'category', label: '屏障级别' }, { prop: 'ownerName', label: '负责人员' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '房间状态' }, { prop: 'remark', label: '房间说明' }]
const formFields = [{ prop: 'recordNo', label: '房间编号' }, { prop: 'recordName', label: '房间名称' }, { prop: 'category', label: '屏障级别' }, { prop: 'ownerName', label: '负责人员' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '房间状态', type: 'select' }, { prop: 'remark', label: '房间说明', type: 'textarea' }]
</script>

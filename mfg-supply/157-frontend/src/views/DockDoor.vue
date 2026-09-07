<template><DataPage title="道口资源" description="道口编号、道口名称、道口类型、负责人、启用时间和道口状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'DISPATCHER', 'YARDMASTER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addDockDoor, deleteDockDoor, finishDockDoor, getDockDoorPage, processDockDoor, updateDockDoor } from '../api'
const api = { page: getDockDoorPage, add: addDockDoor, update: updateDockDoor, delete: deleteDockDoor, process: processDockDoor, finish: finishDockDoor }
const columns = [{ prop: 'recordNo', label: '道口编号' }, { prop: 'recordName', label: '道口名称' }, { prop: 'category', label: '道口类型' }, { prop: 'ownerName', label: '负责人' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '道口状态' }, { prop: 'remark', label: '道口说明' }]
const formFields = [{ prop: 'recordNo', label: '道口编号' }, { prop: 'recordName', label: '道口名称' }, { prop: 'category', label: '道口类型' }, { prop: 'ownerName', label: '负责人' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '道口状态', type: 'select' }, { prop: 'remark', label: '道口说明', type: 'textarea' }]
</script>

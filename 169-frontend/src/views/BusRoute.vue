<template><DataPage title="校车线路" description="线路编号、线路名称、线路类型、负责人员、启用时间和线路状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'SCHOOL', 'DISPATCH', 'DRIVER', 'GUARDIAN']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addBusRoute, deleteBusRoute, finishBusRoute, getBusRoutePage, processBusRoute, updateBusRoute } from '../api'
const api = { page: getBusRoutePage, add: addBusRoute, update: updateBusRoute, delete: deleteBusRoute, process: processBusRoute, finish: finishBusRoute }
const columns = [{ prop: 'recordNo', label: '线路编号' }, { prop: 'recordName', label: '线路名称' }, { prop: 'category', label: '线路类型' }, { prop: 'ownerName', label: '负责人员' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '线路状态' }, { prop: 'remark', label: '线路说明' }]
const formFields = [{ prop: 'recordNo', label: '线路编号' }, { prop: 'recordName', label: '线路名称' }, { prop: 'category', label: '线路类型' }, { prop: 'ownerName', label: '负责人员' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '线路状态', type: 'select' }, { prop: 'remark', label: '线路说明', type: 'textarea' }]
</script>

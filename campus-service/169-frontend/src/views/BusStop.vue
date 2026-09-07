<template><DataPage title="站点档案" description="站点编号、站点名称、站点类型、负责人员、启用时间和站点状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'SCHOOL', 'DISPATCH', 'DRIVER', 'GUARDIAN']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addBusStop, deleteBusStop, finishBusStop, getBusStopPage, processBusStop, updateBusStop } from '../api'
const api = { page: getBusStopPage, add: addBusStop, update: updateBusStop, delete: deleteBusStop, process: processBusStop, finish: finishBusStop }
const columns = [{ prop: 'recordNo', label: '站点编号' }, { prop: 'recordName', label: '站点名称' }, { prop: 'category', label: '站点类型' }, { prop: 'ownerName', label: '负责人员' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '站点状态' }, { prop: 'remark', label: '站点说明' }]
const formFields = [{ prop: 'recordNo', label: '站点编号' }, { prop: 'recordName', label: '站点名称' }, { prop: 'category', label: '站点类型' }, { prop: 'ownerName', label: '负责人员' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '站点状态', type: 'select' }, { prop: 'remark', label: '站点说明', type: 'textarea' }]
</script>

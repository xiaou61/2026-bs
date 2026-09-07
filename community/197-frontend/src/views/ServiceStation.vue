<template><DataPage title="服务站点" description="站点编号、站点名称、服务范围、负责人、建档时间和站点状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'AGENCY']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addServiceStation, deleteServiceStation, finishServiceStation, getServiceStationPage, processServiceStation, updateServiceStation } from '../api'
const api = { page: getServiceStationPage, add: addServiceStation, update: updateServiceStation, delete: deleteServiceStation, process: processServiceStation, finish: finishServiceStation }
const columns = [{ prop: 'recordNo', label: '站点编号' }, { prop: 'recordName', label: '站点名称' }, { prop: 'category', label: '服务范围' }, { prop: 'ownerName', label: '负责人' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '站点状态' }, { prop: 'remark', label: '站点说明' }]
const formFields = [{ prop: 'recordNo', label: '站点编号' }, { prop: 'recordName', label: '站点名称' }, { prop: 'category', label: '服务范围' }, { prop: 'ownerName', label: '负责人' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '站点状态', type: 'select' }, { prop: 'remark', label: '站点说明', type: 'textarea' }]
</script>

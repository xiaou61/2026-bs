<template><DataPage title="服务站点" description="站点编号、站点名称、服务区域、站点负责人、建档时间和站点状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'COMMUNITY']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addServiceCenter, deleteServiceCenter, finishServiceCenter, getServiceCenterPage, processServiceCenter, updateServiceCenter } from '../api'
const api = { page: getServiceCenterPage, add: addServiceCenter, update: updateServiceCenter, delete: deleteServiceCenter, process: processServiceCenter, finish: finishServiceCenter }
const columns = [{ prop: 'recordNo', label: '站点编号' }, { prop: 'recordName', label: '站点名称' }, { prop: 'category', label: '服务区域' }, { prop: 'ownerName', label: '站点负责人' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '站点状态' }, { prop: 'remark', label: '站点说明' }]
const formFields = [{ prop: 'recordNo', label: '站点编号' }, { prop: 'recordName', label: '站点名称' }, { prop: 'category', label: '服务区域' }, { prop: 'ownerName', label: '站点负责人' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '站点状态', type: 'select' }, { prop: 'remark', label: '站点说明', type: 'textarea' }]
</script>

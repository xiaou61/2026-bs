<template><DataPage title="异常占位" description="异常编号、占用车位、异常类型、上报人员、上报时间和异常状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'PARKING', 'PATROL', 'TENANT', 'SERVICE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addAbnormalOccupancy, deleteAbnormalOccupancy, finishAbnormalOccupancy, getAbnormalOccupancyPage, processAbnormalOccupancy, updateAbnormalOccupancy } from '../api'
const api = { page: getAbnormalOccupancyPage, add: addAbnormalOccupancy, update: updateAbnormalOccupancy, delete: deleteAbnormalOccupancy, process: processAbnormalOccupancy, finish: finishAbnormalOccupancy }
const columns = [{ prop: 'recordNo', label: '异常编号' }, { prop: 'recordName', label: '占用车位' }, { prop: 'category', label: '异常类型' }, { prop: 'ownerName', label: '上报人员' }, { prop: 'planTime', label: '上报时间' }, { prop: 'status', label: '异常状态' }, { prop: 'remark', label: '异常说明' }]
const formFields = [{ prop: 'recordNo', label: '异常编号' }, { prop: 'recordName', label: '占用车位' }, { prop: 'category', label: '异常类型' }, { prop: 'ownerName', label: '上报人员' }, { prop: 'planTime', label: '上报时间' }, { prop: 'status', label: '异常状态', type: 'select' }, { prop: 'remark', label: '异常说明', type: 'textarea' }]
</script>

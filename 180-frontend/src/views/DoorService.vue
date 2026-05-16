<template><DataPage title="上门处理" description="服务编号、报修工单、服务类型、维修人员、上门时间和处理状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'PROPERTY', 'DISPATCH', 'WORKER', 'OWNER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addDoorService, deleteDoorService, finishDoorService, getDoorServicePage, processDoorService, updateDoorService } from '../api'
const api = { page: getDoorServicePage, add: addDoorService, update: updateDoorService, delete: deleteDoorService, process: processDoorService, finish: finishDoorService }
const columns = [{ prop: 'recordNo', label: '服务编号' }, { prop: 'recordName', label: '报修工单' }, { prop: 'category', label: '服务类型' }, { prop: 'ownerName', label: '维修人员' }, { prop: 'planTime', label: '上门时间' }, { prop: 'status', label: '处理状态' }, { prop: 'remark', label: '处理说明' }]
const formFields = [{ prop: 'recordNo', label: '服务编号' }, { prop: 'recordName', label: '报修工单' }, { prop: 'category', label: '服务类型' }, { prop: 'ownerName', label: '维修人员' }, { prop: 'planTime', label: '上门时间' }, { prop: 'status', label: '处理状态', type: 'select' }, { prop: 'remark', label: '处理说明', type: 'textarea' }]
</script>

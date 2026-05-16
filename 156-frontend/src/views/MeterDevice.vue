<template><DataPage title="能耗表计" description="表计编号、表计名称、表计类型、维护人、安装时间和表计状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'LOGISTICS', 'ENERGY']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addMeterDevice, deleteMeterDevice, finishMeterDevice, getMeterDevicePage, processMeterDevice, updateMeterDevice } from '../api'
const api = { page: getMeterDevicePage, add: addMeterDevice, update: updateMeterDevice, delete: deleteMeterDevice, process: processMeterDevice, finish: finishMeterDevice }
const columns = [{ prop: 'recordNo', label: '表计编号' }, { prop: 'recordName', label: '表计名称' }, { prop: 'category', label: '表计类型' }, { prop: 'ownerName', label: '维护人' }, { prop: 'planTime', label: '安装时间' }, { prop: 'status', label: '表计状态' }, { prop: 'remark', label: '表计说明' }]
const formFields = [{ prop: 'recordNo', label: '表计编号' }, { prop: 'recordName', label: '表计名称' }, { prop: 'category', label: '表计类型' }, { prop: 'ownerName', label: '维护人' }, { prop: 'planTime', label: '安装时间' }, { prop: 'status', label: '表计状态', type: 'select' }, { prop: 'remark', label: '表计说明', type: 'textarea' }]
</script>

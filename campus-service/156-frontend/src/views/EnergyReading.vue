<template><DataPage title="用电读数" description="读数编号、表计房间、读数类型、采集人、采集时间和读数状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'LOGISTICS', 'ENERGY', 'STUDENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addEnergyReading, deleteEnergyReading, finishEnergyReading, getEnergyReadingPage, processEnergyReading, updateEnergyReading } from '../api'
const api = { page: getEnergyReadingPage, add: addEnergyReading, update: updateEnergyReading, delete: deleteEnergyReading, process: processEnergyReading, finish: finishEnergyReading }
const columns = [{ prop: 'recordNo', label: '读数编号' }, { prop: 'recordName', label: '表计房间' }, { prop: 'category', label: '读数类型' }, { prop: 'ownerName', label: '采集人' }, { prop: 'planTime', label: '采集时间' }, { prop: 'status', label: '读数状态' }, { prop: 'remark', label: '读数说明' }]
const formFields = [{ prop: 'recordNo', label: '读数编号' }, { prop: 'recordName', label: '表计房间' }, { prop: 'category', label: '读数类型' }, { prop: 'ownerName', label: '采集人' }, { prop: 'planTime', label: '采集时间' }, { prop: 'status', label: '读数状态', type: 'select' }, { prop: 'remark', label: '读数说明', type: 'textarea' }]
</script>

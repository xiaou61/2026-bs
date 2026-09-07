<template><DataPage title="风险源台账" description="风险编号、风险源名称、风险类型、责任人、巡检周期和管控状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'SAFETY', 'GUARDIAN']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addHazardSource, deleteHazardSource, finishHazardSource, getHazardSourcePage, processHazardSource, updateHazardSource } from '../api'
const api = { page: getHazardSourcePage, add: addHazardSource, update: updateHazardSource, delete: deleteHazardSource, process: processHazardSource, finish: finishHazardSource }
const columns = [{ prop: 'recordNo', label: '风险编号' }, { prop: 'recordName', label: '风险源名称' }, { prop: 'category', label: '风险类型' }, { prop: 'ownerName', label: '责任人' }, { prop: 'planTime', label: '巡检周期' }, { prop: 'status', label: '管控状态' }, { prop: 'remark', label: '风险说明' }]
const formFields = [{ prop: 'recordNo', label: '风险编号' }, { prop: 'recordName', label: '风险源名称' }, { prop: 'category', label: '风险类型' }, { prop: 'ownerName', label: '责任人' }, { prop: 'planTime', label: '巡检周期' }, { prop: 'status', label: '管控状态', type: 'select' }, { prop: 'remark', label: '风险说明', type: 'textarea' }]
</script>

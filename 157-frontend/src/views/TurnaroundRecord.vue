<template><DataPage title="周转统计" description="统计编号、统计车辆、统计类型、统计人、统计周期和统计状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'DISPATCHER', 'YARDMASTER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addTurnaroundRecord, deleteTurnaroundRecord, finishTurnaroundRecord, getTurnaroundRecordPage, processTurnaroundRecord, updateTurnaroundRecord } from '../api'
const api = { page: getTurnaroundRecordPage, add: addTurnaroundRecord, update: updateTurnaroundRecord, delete: deleteTurnaroundRecord, process: processTurnaroundRecord, finish: finishTurnaroundRecord }
const columns = [{ prop: 'recordNo', label: '统计编号' }, { prop: 'recordName', label: '统计车辆' }, { prop: 'category', label: '统计类型' }, { prop: 'ownerName', label: '统计人' }, { prop: 'planTime', label: '统计周期' }, { prop: 'status', label: '统计状态' }, { prop: 'remark', label: '统计说明' }]
const formFields = [{ prop: 'recordNo', label: '统计编号' }, { prop: 'recordName', label: '统计车辆' }, { prop: 'category', label: '统计类型' }, { prop: 'ownerName', label: '统计人' }, { prop: 'planTime', label: '统计周期' }, { prop: 'status', label: '统计状态', type: 'select' }, { prop: 'remark', label: '统计说明', type: 'textarea' }]
</script>

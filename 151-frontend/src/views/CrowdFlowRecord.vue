<template><DataPage title="客流统计" description="统计编号、场馆区域、客流类型、统计员、统计时段和预警状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'MANAGER', 'CHECKER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addCrowdFlowRecord, deleteCrowdFlowRecord, finishCrowdFlowRecord, getCrowdFlowRecordPage, processCrowdFlowRecord, updateCrowdFlowRecord } from '../api'
const api = { page: getCrowdFlowRecordPage, add: addCrowdFlowRecord, update: updateCrowdFlowRecord, delete: deleteCrowdFlowRecord, process: processCrowdFlowRecord, finish: finishCrowdFlowRecord }
const columns = [{ prop: 'recordNo', label: '统计编号' }, { prop: 'recordName', label: '场馆区域' }, { prop: 'category', label: '客流类型' }, { prop: 'ownerName', label: '统计员' }, { prop: 'planTime', label: '统计时段' }, { prop: 'status', label: '预警状态' }, { prop: 'remark', label: '统计说明' }]
const formFields = [{ prop: 'recordNo', label: '统计编号' }, { prop: 'recordName', label: '场馆区域' }, { prop: 'category', label: '客流类型' }, { prop: 'ownerName', label: '统计员' }, { prop: 'planTime', label: '统计时段' }, { prop: 'status', label: '预警状态', type: 'select' }, { prop: 'remark', label: '统计说明', type: 'textarea' }]
</script>

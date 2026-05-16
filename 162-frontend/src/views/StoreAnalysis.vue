<template><DataPage title="门店分析" description="分析编号、分析门店、分析类型、分析人、分析时间和分析状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'MANAGER', 'MARKETING']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addStoreAnalysis, deleteStoreAnalysis, finishStoreAnalysis, getStoreAnalysisPage, processStoreAnalysis, updateStoreAnalysis } from '../api'
const api = { page: getStoreAnalysisPage, add: addStoreAnalysis, update: updateStoreAnalysis, delete: deleteStoreAnalysis, process: processStoreAnalysis, finish: finishStoreAnalysis }
const columns = [{ prop: 'recordNo', label: '分析编号' }, { prop: 'recordName', label: '分析门店' }, { prop: 'category', label: '分析类型' }, { prop: 'ownerName', label: '分析人' }, { prop: 'planTime', label: '分析时间' }, { prop: 'status', label: '分析状态' }, { prop: 'remark', label: '分析说明' }]
const formFields = [{ prop: 'recordNo', label: '分析编号' }, { prop: 'recordName', label: '分析门店' }, { prop: 'category', label: '分析类型' }, { prop: 'ownerName', label: '分析人' }, { prop: 'planTime', label: '分析时间' }, { prop: 'status', label: '分析状态', type: 'select' }, { prop: 'remark', label: '分析说明', type: 'textarea' }]
</script>

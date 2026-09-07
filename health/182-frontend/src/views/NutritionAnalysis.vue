<template><DataPage title="营养分析" description="分析编号、助餐订单、分析类型、营养师、分析时间和分析状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CENTER', 'DIETITIAN', 'ELDER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addNutritionAnalysis, deleteNutritionAnalysis, finishNutritionAnalysis, getNutritionAnalysisPage, processNutritionAnalysis, updateNutritionAnalysis } from '../api'
const api = { page: getNutritionAnalysisPage, add: addNutritionAnalysis, update: updateNutritionAnalysis, delete: deleteNutritionAnalysis, process: processNutritionAnalysis, finish: finishNutritionAnalysis }
const columns = [{ prop: 'recordNo', label: '分析编号' }, { prop: 'recordName', label: '助餐订单' }, { prop: 'category', label: '分析类型' }, { prop: 'ownerName', label: '营养师' }, { prop: 'planTime', label: '分析时间' }, { prop: 'status', label: '分析状态' }, { prop: 'remark', label: '分析说明' }]
const formFields = [{ prop: 'recordNo', label: '分析编号' }, { prop: 'recordName', label: '助餐订单' }, { prop: 'category', label: '分析类型' }, { prop: 'ownerName', label: '营养师' }, { prop: 'planTime', label: '分析时间' }, { prop: 'status', label: '分析状态', type: 'select' }, { prop: 'remark', label: '分析说明', type: 'textarea' }]
</script>

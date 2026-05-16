<template><DataPage title="营养套餐" description="套餐编号、套餐名称、膳食类型、营养师、上架时间和套餐状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CENTER', 'DIETITIAN', 'ELDER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addNutritionMenu, deleteNutritionMenu, finishNutritionMenu, getNutritionMenuPage, processNutritionMenu, updateNutritionMenu } from '../api'
const api = { page: getNutritionMenuPage, add: addNutritionMenu, update: updateNutritionMenu, delete: deleteNutritionMenu, process: processNutritionMenu, finish: finishNutritionMenu }
const columns = [{ prop: 'recordNo', label: '套餐编号' }, { prop: 'recordName', label: '套餐名称' }, { prop: 'category', label: '膳食类型' }, { prop: 'ownerName', label: '营养师' }, { prop: 'planTime', label: '上架时间' }, { prop: 'status', label: '套餐状态' }, { prop: 'remark', label: '套餐说明' }]
const formFields = [{ prop: 'recordNo', label: '套餐编号' }, { prop: 'recordName', label: '套餐名称' }, { prop: 'category', label: '膳食类型' }, { prop: 'ownerName', label: '营养师' }, { prop: 'planTime', label: '上架时间' }, { prop: 'status', label: '套餐状态', type: 'select' }, { prop: 'remark', label: '套餐说明', type: 'textarea' }]
</script>

<template><DataPage title="物料使用" description="物料编号、维修工单、物料类型、领用人员、使用时间和物料状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'PROPERTY', 'WORKER', 'FINANCE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addMaterialUsage, deleteMaterialUsage, finishMaterialUsage, getMaterialUsagePage, processMaterialUsage, updateMaterialUsage } from '../api'
const api = { page: getMaterialUsagePage, add: addMaterialUsage, update: updateMaterialUsage, delete: deleteMaterialUsage, process: processMaterialUsage, finish: finishMaterialUsage }
const columns = [{ prop: 'recordNo', label: '物料编号' }, { prop: 'recordName', label: '维修工单' }, { prop: 'category', label: '物料类型' }, { prop: 'ownerName', label: '领用人员' }, { prop: 'planTime', label: '使用时间' }, { prop: 'status', label: '物料状态' }, { prop: 'remark', label: '物料说明' }]
const formFields = [{ prop: 'recordNo', label: '物料编号' }, { prop: 'recordName', label: '维修工单' }, { prop: 'category', label: '物料类型' }, { prop: 'ownerName', label: '领用人员' }, { prop: 'planTime', label: '使用时间' }, { prop: 'status', label: '物料状态', type: 'select' }, { prop: 'remark', label: '物料说明', type: 'textarea' }]
</script>

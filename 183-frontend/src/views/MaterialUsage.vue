<template><DataPage title="耗材使用" description="使用编号、动物房间、耗材类型、领用人员、使用时间和使用状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'LAB', 'BREEDER', 'RESEARCHER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addMaterialUsage, deleteMaterialUsage, finishMaterialUsage, getMaterialUsagePage, processMaterialUsage, updateMaterialUsage } from '../api'
const api = { page: getMaterialUsagePage, add: addMaterialUsage, update: updateMaterialUsage, delete: deleteMaterialUsage, process: processMaterialUsage, finish: finishMaterialUsage }
const columns = [{ prop: 'recordNo', label: '使用编号' }, { prop: 'recordName', label: '动物房间' }, { prop: 'category', label: '耗材类型' }, { prop: 'ownerName', label: '领用人员' }, { prop: 'planTime', label: '使用时间' }, { prop: 'status', label: '使用状态' }, { prop: 'remark', label: '使用说明' }]
const formFields = [{ prop: 'recordNo', label: '使用编号' }, { prop: 'recordName', label: '动物房间' }, { prop: 'category', label: '耗材类型' }, { prop: 'ownerName', label: '领用人员' }, { prop: 'planTime', label: '使用时间' }, { prop: 'status', label: '使用状态', type: 'select' }, { prop: 'remark', label: '使用说明', type: 'textarea' }]
</script>

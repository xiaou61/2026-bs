<template><DataPage title="库存批次" description="批次编号、物资名称、储备仓库、仓储人员、入库时间和批次状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'WAREHOUSE', 'CHECKER', 'DISPATCH', 'AUDITOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addStockBatch, deleteStockBatch, finishStockBatch, getStockBatchPage, processStockBatch, updateStockBatch } from '../api'
const api = { page: getStockBatchPage, add: addStockBatch, update: updateStockBatch, delete: deleteStockBatch, process: processStockBatch, finish: finishStockBatch }
const columns = [{ prop: 'recordNo', label: '批次编号' }, { prop: 'recordName', label: '物资名称' }, { prop: 'category', label: '储备仓库' }, { prop: 'ownerName', label: '仓储人员' }, { prop: 'planTime', label: '入库时间' }, { prop: 'status', label: '批次状态' }, { prop: 'remark', label: '批次说明' }]
const formFields = [{ prop: 'recordNo', label: '批次编号' }, { prop: 'recordName', label: '物资名称' }, { prop: 'category', label: '储备仓库' }, { prop: 'ownerName', label: '仓储人员' }, { prop: 'planTime', label: '入库时间' }, { prop: 'status', label: '批次状态', type: 'select' }, { prop: 'remark', label: '批次说明', type: 'textarea' }]
</script>

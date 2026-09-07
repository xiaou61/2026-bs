<template><DataPage title="库存预警" description="预警编号、预警物资、预警类型、责任人员、预警时间和预警状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'WAREHOUSE', 'CHECKER', 'DISPATCH', 'AUDITOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addStockWarning, deleteStockWarning, finishStockWarning, getStockWarningPage, processStockWarning, updateStockWarning } from '../api'
const api = { page: getStockWarningPage, add: addStockWarning, update: updateStockWarning, delete: deleteStockWarning, process: processStockWarning, finish: finishStockWarning }
const columns = [{ prop: 'recordNo', label: '预警编号' }, { prop: 'recordName', label: '预警物资' }, { prop: 'category', label: '预警类型' }, { prop: 'ownerName', label: '责任人员' }, { prop: 'planTime', label: '预警时间' }, { prop: 'status', label: '预警状态' }, { prop: 'remark', label: '预警说明' }]
const formFields = [{ prop: 'recordNo', label: '预警编号' }, { prop: 'recordName', label: '预警物资' }, { prop: 'category', label: '预警类型' }, { prop: 'ownerName', label: '责任人员' }, { prop: 'planTime', label: '预警时间' }, { prop: 'status', label: '预警状态', type: 'select' }, { prop: 'remark', label: '预警说明', type: 'textarea' }]
</script>

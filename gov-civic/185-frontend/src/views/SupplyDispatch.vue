<template><DataPage title="补给调度" description="调度编号、补给申请、调度类型、补给人员、调度时间和调度状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'SANITATION', 'SUPPLY']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addSupplyDispatch, deleteSupplyDispatch, finishSupplyDispatch, getSupplyDispatchPage, processSupplyDispatch, updateSupplyDispatch } from '../api'
const api = { page: getSupplyDispatchPage, add: addSupplyDispatch, update: updateSupplyDispatch, delete: deleteSupplyDispatch, process: processSupplyDispatch, finish: finishSupplyDispatch }
const columns = [{ prop: 'recordNo', label: '调度编号' }, { prop: 'recordName', label: '补给申请' }, { prop: 'category', label: '调度类型' }, { prop: 'ownerName', label: '补给人员' }, { prop: 'planTime', label: '调度时间' }, { prop: 'status', label: '调度状态' }, { prop: 'remark', label: '调度说明' }]
const formFields = [{ prop: 'recordNo', label: '调度编号' }, { prop: 'recordName', label: '补给申请' }, { prop: 'category', label: '调度类型' }, { prop: 'ownerName', label: '补给人员' }, { prop: 'planTime', label: '调度时间' }, { prop: 'status', label: '调度状态', type: 'select' }, { prop: 'remark', label: '调度说明', type: 'textarea' }]
</script>

<template><DataPage title="补给申请" description="申请编号、公厕点位、补给类型、申请人员、申请时间和申请状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'SANITATION', 'SUPPLY', 'CLEANER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addSupplyRequest, deleteSupplyRequest, finishSupplyRequest, getSupplyRequestPage, processSupplyRequest, updateSupplyRequest } from '../api'
const api = { page: getSupplyRequestPage, add: addSupplyRequest, update: updateSupplyRequest, delete: deleteSupplyRequest, process: processSupplyRequest, finish: finishSupplyRequest }
const columns = [{ prop: 'recordNo', label: '申请编号' }, { prop: 'recordName', label: '公厕点位' }, { prop: 'category', label: '补给类型' }, { prop: 'ownerName', label: '申请人员' }, { prop: 'planTime', label: '申请时间' }, { prop: 'status', label: '申请状态' }, { prop: 'remark', label: '申请说明' }]
const formFields = [{ prop: 'recordNo', label: '申请编号' }, { prop: 'recordName', label: '公厕点位' }, { prop: 'category', label: '补给类型' }, { prop: 'ownerName', label: '申请人员' }, { prop: 'planTime', label: '申请时间' }, { prop: 'status', label: '申请状态', type: 'select' }, { prop: 'remark', label: '申请说明', type: 'textarea' }]
</script>

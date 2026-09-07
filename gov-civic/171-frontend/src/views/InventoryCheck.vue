<template><DataPage title="库存盘点" description="盘点编号、盘点仓库、盘点类型、盘点人员、盘点时间和盘点状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'WAREHOUSE', 'CHECKER', 'AUDITOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addInventoryCheck, deleteInventoryCheck, finishInventoryCheck, getInventoryCheckPage, processInventoryCheck, updateInventoryCheck } from '../api'
const api = { page: getInventoryCheckPage, add: addInventoryCheck, update: updateInventoryCheck, delete: deleteInventoryCheck, process: processInventoryCheck, finish: finishInventoryCheck }
const columns = [{ prop: 'recordNo', label: '盘点编号' }, { prop: 'recordName', label: '盘点仓库' }, { prop: 'category', label: '盘点类型' }, { prop: 'ownerName', label: '盘点人员' }, { prop: 'planTime', label: '盘点时间' }, { prop: 'status', label: '盘点状态' }, { prop: 'remark', label: '盘点说明' }]
const formFields = [{ prop: 'recordNo', label: '盘点编号' }, { prop: 'recordName', label: '盘点仓库' }, { prop: 'category', label: '盘点类型' }, { prop: 'ownerName', label: '盘点人员' }, { prop: 'planTime', label: '盘点时间' }, { prop: 'status', label: '盘点状态', type: 'select' }, { prop: 'remark', label: '盘点说明', type: 'textarea' }]
</script>

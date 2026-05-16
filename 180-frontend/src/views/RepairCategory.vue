<template><DataPage title="报修分类" description="分类编号、分类名称、维修类型、负责班组、启用时间和分类状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'PROPERTY', 'DISPATCH', 'WORKER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addRepairCategory, deleteRepairCategory, finishRepairCategory, getRepairCategoryPage, processRepairCategory, updateRepairCategory } from '../api'
const api = { page: getRepairCategoryPage, add: addRepairCategory, update: updateRepairCategory, delete: deleteRepairCategory, process: processRepairCategory, finish: finishRepairCategory }
const columns = [{ prop: 'recordNo', label: '分类编号' }, { prop: 'recordName', label: '分类名称' }, { prop: 'category', label: '维修类型' }, { prop: 'ownerName', label: '负责班组' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '分类状态' }, { prop: 'remark', label: '分类说明' }]
const formFields = [{ prop: 'recordNo', label: '分类编号' }, { prop: 'recordName', label: '分类名称' }, { prop: 'category', label: '维修类型' }, { prop: 'ownerName', label: '负责班组' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '分类状态', type: 'select' }, { prop: 'remark', label: '分类说明', type: 'textarea' }]
</script>

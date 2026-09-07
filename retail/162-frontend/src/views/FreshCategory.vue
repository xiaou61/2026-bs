<template><DataPage title="生鲜品类" description="品类编号、品类名称、温控类型、负责人、启用时间和品类状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'MANAGER', 'STOCK']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addFreshCategory, deleteFreshCategory, finishFreshCategory, getFreshCategoryPage, processFreshCategory, updateFreshCategory } from '../api'
const api = { page: getFreshCategoryPage, add: addFreshCategory, update: updateFreshCategory, delete: deleteFreshCategory, process: processFreshCategory, finish: finishFreshCategory }
const columns = [{ prop: 'recordNo', label: '品类编号' }, { prop: 'recordName', label: '品类名称' }, { prop: 'category', label: '温控类型' }, { prop: 'ownerName', label: '负责人' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '品类状态' }, { prop: 'remark', label: '品类说明' }]
const formFields = [{ prop: 'recordNo', label: '品类编号' }, { prop: 'recordName', label: '品类名称' }, { prop: 'category', label: '温控类型' }, { prop: 'ownerName', label: '负责人' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '品类状态', type: 'select' }, { prop: 'remark', label: '品类说明', type: 'textarea' }]
</script>

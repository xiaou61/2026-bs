<template><DataPage title="物品分类" description="分类编码、分类名称、适用品类、维护人、更新时间和启用状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'OPERATOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addItemCategory, deleteItemCategory, finishItemCategory, getItemCategoryPage, processItemCategory, updateItemCategory } from '../api'
const api = { page: getItemCategoryPage, add: addItemCategory, update: updateItemCategory, delete: deleteItemCategory, process: processItemCategory, finish: finishItemCategory }
const columns = [{ prop: 'recordNo', label: '分类编码' }, { prop: 'recordName', label: '分类名称' }, { prop: 'category', label: '适用品类' }, { prop: 'ownerName', label: '维护人' }, { prop: 'planTime', label: '更新时间' }, { prop: 'status', label: '启用状态' }, { prop: 'remark', label: '分类说明' }]
const formFields = [{ prop: 'recordNo', label: '分类编码' }, { prop: 'recordName', label: '分类名称' }, { prop: 'category', label: '适用品类' }, { prop: 'ownerName', label: '维护人' }, { prop: 'planTime', label: '更新时间' }, { prop: 'status', label: '启用状态', type: 'select' }, { prop: 'remark', label: '分类说明', type: 'textarea' }]
</script>

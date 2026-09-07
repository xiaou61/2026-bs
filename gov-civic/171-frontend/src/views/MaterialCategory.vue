<template><DataPage title="物资分类" description="分类编号、分类名称、保障类型、负责人员、启用时间和分类状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'WAREHOUSE', 'DISPATCH', 'AUDITOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addMaterialCategory, deleteMaterialCategory, finishMaterialCategory, getMaterialCategoryPage, processMaterialCategory, updateMaterialCategory } from '../api'
const api = { page: getMaterialCategoryPage, add: addMaterialCategory, update: updateMaterialCategory, delete: deleteMaterialCategory, process: processMaterialCategory, finish: finishMaterialCategory }
const columns = [{ prop: 'recordNo', label: '分类编号' }, { prop: 'recordName', label: '分类名称' }, { prop: 'category', label: '保障类型' }, { prop: 'ownerName', label: '负责人员' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '分类状态' }, { prop: 'remark', label: '分类说明' }]
const formFields = [{ prop: 'recordNo', label: '分类编号' }, { prop: 'recordName', label: '分类名称' }, { prop: 'category', label: '保障类型' }, { prop: 'ownerName', label: '负责人员' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '分类状态', type: 'select' }, { prop: 'remark', label: '分类说明', type: 'textarea' }]
</script>

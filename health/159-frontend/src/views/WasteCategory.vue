<template><DataPage title="废物类别" description="类别编号、类别名称、危险类型、管理人、启用时间和类别状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'HOSPITAL', 'COLLECTOR', 'REGULATOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addWasteCategory, deleteWasteCategory, finishWasteCategory, getWasteCategoryPage, processWasteCategory, updateWasteCategory } from '../api'
const api = { page: getWasteCategoryPage, add: addWasteCategory, update: updateWasteCategory, delete: deleteWasteCategory, process: processWasteCategory, finish: finishWasteCategory }
const columns = [{ prop: 'recordNo', label: '类别编号' }, { prop: 'recordName', label: '类别名称' }, { prop: 'category', label: '危险类型' }, { prop: 'ownerName', label: '管理人' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '类别状态' }, { prop: 'remark', label: '类别说明' }]
const formFields = [{ prop: 'recordNo', label: '类别编号' }, { prop: 'recordName', label: '类别名称' }, { prop: 'category', label: '危险类型' }, { prop: 'ownerName', label: '管理人' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '类别状态', type: 'select' }, { prop: 'remark', label: '类别说明', type: 'textarea' }]
</script>

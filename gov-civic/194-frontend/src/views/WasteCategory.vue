<template><DataPage title="危废类别" description="危废编号、危废名称、危废类别、责任人员、建档时间和风险状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'PARK', 'WAREHOUSE', 'ENTERPRISE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addWasteCategory, deleteWasteCategory, finishWasteCategory, getWasteCategoryPage, processWasteCategory, updateWasteCategory } from '../api'
const api = { page: getWasteCategoryPage, add: addWasteCategory, update: updateWasteCategory, delete: deleteWasteCategory, process: processWasteCategory, finish: finishWasteCategory }
const columns = [{ prop: 'recordNo', label: '危废编号' }, { prop: 'recordName', label: '危废名称' }, { prop: 'category', label: '危废类别' }, { prop: 'ownerName', label: '责任人员' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '风险状态' }, { prop: 'remark', label: '危废说明' }]
const formFields = [{ prop: 'recordNo', label: '危废编号' }, { prop: 'recordName', label: '危废名称' }, { prop: 'category', label: '危废类别' }, { prop: 'ownerName', label: '责任人员' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '风险状态', type: 'select' }, { prop: 'remark', label: '危废说明', type: 'textarea' }]
</script>

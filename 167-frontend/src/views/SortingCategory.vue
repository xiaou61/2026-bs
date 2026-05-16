<template><DataPage title="分类规则" description="规则编号、垃圾类别、投放要求、责任人员、生效时间和规则状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'COMMUNITY', 'SUPERVISOR', 'VOLUNTEER', 'RESIDENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addSortingCategory, deleteSortingCategory, finishSortingCategory, getSortingCategoryPage, processSortingCategory, updateSortingCategory } from '../api'
const api = { page: getSortingCategoryPage, add: addSortingCategory, update: updateSortingCategory, delete: deleteSortingCategory, process: processSortingCategory, finish: finishSortingCategory }
const columns = [{ prop: 'recordNo', label: '规则编号' }, { prop: 'recordName', label: '垃圾类别' }, { prop: 'category', label: '投放要求' }, { prop: 'ownerName', label: '责任人员' }, { prop: 'planTime', label: '生效时间' }, { prop: 'status', label: '规则状态' }, { prop: 'remark', label: '规则说明' }]
const formFields = [{ prop: 'recordNo', label: '规则编号' }, { prop: 'recordName', label: '垃圾类别' }, { prop: 'category', label: '投放要求' }, { prop: 'ownerName', label: '责任人员' }, { prop: 'planTime', label: '生效时间' }, { prop: 'status', label: '规则状态', type: 'select' }, { prop: 'remark', label: '规则说明', type: 'textarea' }]
</script>

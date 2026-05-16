<template><DataPage title="器具借用" description="借用编号、借用居民、器具类型、申请人员、申请时间和借用状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'COMMUNITY', 'AIDSTAFF', 'RESIDENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addBorrowApplication, deleteBorrowApplication, finishBorrowApplication, getBorrowApplicationPage, processBorrowApplication, updateBorrowApplication } from '../api'
const api = { page: getBorrowApplicationPage, add: addBorrowApplication, update: updateBorrowApplication, delete: deleteBorrowApplication, process: processBorrowApplication, finish: finishBorrowApplication }
const columns = [{ prop: 'recordNo', label: '借用编号' }, { prop: 'recordName', label: '借用居民' }, { prop: 'category', label: '器具类型' }, { prop: 'ownerName', label: '申请人员' }, { prop: 'planTime', label: '申请时间' }, { prop: 'status', label: '借用状态' }, { prop: 'remark', label: '借用说明' }]
const formFields = [{ prop: 'recordNo', label: '借用编号' }, { prop: 'recordName', label: '借用居民' }, { prop: 'category', label: '器具类型' }, { prop: 'ownerName', label: '申请人员' }, { prop: 'planTime', label: '申请时间' }, { prop: 'status', label: '借用状态', type: 'select' }, { prop: 'remark', label: '借用说明', type: 'textarea' }]
</script>

<template><DataPage title="物资借用" description="借用编号、借用物资、借用类型、借用人、借用时间和借用状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'UNION', 'CLUB', 'WAREHOUSE', 'MEMBER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addBorrowRequest, deleteBorrowRequest, finishBorrowRequest, getBorrowRequestPage, processBorrowRequest, updateBorrowRequest } from '../api'
const api = { page: getBorrowRequestPage, add: addBorrowRequest, update: updateBorrowRequest, delete: deleteBorrowRequest, process: processBorrowRequest, finish: finishBorrowRequest }
const columns = [{ prop: 'recordNo', label: '借用编号' }, { prop: 'recordName', label: '借用物资' }, { prop: 'category', label: '借用类型' }, { prop: 'ownerName', label: '借用人' }, { prop: 'planTime', label: '借用时间' }, { prop: 'status', label: '借用状态' }, { prop: 'remark', label: '借用说明' }]
const formFields = [{ prop: 'recordNo', label: '借用编号' }, { prop: 'recordName', label: '借用物资' }, { prop: 'category', label: '借用类型' }, { prop: 'ownerName', label: '借用人' }, { prop: 'planTime', label: '借用时间' }, { prop: 'status', label: '借用状态', type: 'select' }, { prop: 'remark', label: '借用说明', type: 'textarea' }]
</script>

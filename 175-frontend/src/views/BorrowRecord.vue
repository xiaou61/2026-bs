<template><DataPage title="借阅记录" description="借阅编号、借阅图书、借阅类型、借阅读者、借阅时间和借阅状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'LIBRARY', 'CURATOR', 'STUDENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addBorrowRecord, deleteBorrowRecord, finishBorrowRecord, getBorrowRecordPage, processBorrowRecord, updateBorrowRecord } from '../api'
const api = { page: getBorrowRecordPage, add: addBorrowRecord, update: updateBorrowRecord, delete: deleteBorrowRecord, process: processBorrowRecord, finish: finishBorrowRecord }
const columns = [{ prop: 'recordNo', label: '借阅编号' }, { prop: 'recordName', label: '借阅图书' }, { prop: 'category', label: '借阅类型' }, { prop: 'ownerName', label: '借阅读者' }, { prop: 'planTime', label: '借阅时间' }, { prop: 'status', label: '借阅状态' }, { prop: 'remark', label: '借阅说明' }]
const formFields = [{ prop: 'recordNo', label: '借阅编号' }, { prop: 'recordName', label: '借阅图书' }, { prop: 'category', label: '借阅类型' }, { prop: 'ownerName', label: '借阅读者' }, { prop: 'planTime', label: '借阅时间' }, { prop: 'status', label: '借阅状态', type: 'select' }, { prop: 'remark', label: '借阅说明', type: 'textarea' }]
</script>

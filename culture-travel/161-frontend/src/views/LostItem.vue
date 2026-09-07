<template><DataPage title="失物登记" description="失物编号、失物名称、失物类型、登记人、丢失时间和失物状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'SERVICE', 'SECURITY', 'VISITOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addLostItem, deleteLostItem, finishLostItem, getLostItemPage, processLostItem, updateLostItem } from '../api'
const api = { page: getLostItemPage, add: addLostItem, update: updateLostItem, delete: deleteLostItem, process: processLostItem, finish: finishLostItem }
const columns = [{ prop: 'recordNo', label: '失物编号' }, { prop: 'recordName', label: '失物名称' }, { prop: 'category', label: '失物类型' }, { prop: 'ownerName', label: '登记人' }, { prop: 'planTime', label: '丢失时间' }, { prop: 'status', label: '失物状态' }, { prop: 'remark', label: '失物说明' }]
const formFields = [{ prop: 'recordNo', label: '失物编号' }, { prop: 'recordName', label: '失物名称' }, { prop: 'category', label: '失物类型' }, { prop: 'ownerName', label: '登记人' }, { prop: 'planTime', label: '丢失时间' }, { prop: 'status', label: '失物状态', type: 'select' }, { prop: 'remark', label: '失物说明', type: 'textarea' }]
</script>

<template><DataPage title="耗材目录" description="耗材编号、耗材名称、耗材类别、管理人员、启用时间和耗材状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'NURSE', 'DENTIST', 'CASHIER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addConsumableCatalog, deleteConsumableCatalog, finishConsumableCatalog, getConsumableCatalogPage, processConsumableCatalog, updateConsumableCatalog } from '../api'
const api = { page: getConsumableCatalogPage, add: addConsumableCatalog, update: updateConsumableCatalog, delete: deleteConsumableCatalog, process: processConsumableCatalog, finish: finishConsumableCatalog }
const columns = [{ prop: 'recordNo', label: '耗材编号' }, { prop: 'recordName', label: '耗材名称' }, { prop: 'category', label: '耗材类别' }, { prop: 'ownerName', label: '管理人员' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '耗材状态' }, { prop: 'remark', label: '耗材说明' }]
const formFields = [{ prop: 'recordNo', label: '耗材编号' }, { prop: 'recordName', label: '耗材名称' }, { prop: 'category', label: '耗材类别' }, { prop: 'ownerName', label: '管理人员' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '耗材状态', type: 'select' }, { prop: 'remark', label: '耗材说明', type: 'textarea' }]
</script>

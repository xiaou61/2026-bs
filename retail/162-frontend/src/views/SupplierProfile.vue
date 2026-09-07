<template><DataPage title="供应商档案" description="供应商编号、供应商名称、供货品类、联系人、合作时间和合作状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'MANAGER', 'SUPPLIER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addSupplierProfile, deleteSupplierProfile, finishSupplierProfile, getSupplierProfilePage, processSupplierProfile, updateSupplierProfile } from '../api'
const api = { page: getSupplierProfilePage, add: addSupplierProfile, update: updateSupplierProfile, delete: deleteSupplierProfile, process: processSupplierProfile, finish: finishSupplierProfile }
const columns = [{ prop: 'recordNo', label: '供应商编号' }, { prop: 'recordName', label: '供应商名称' }, { prop: 'category', label: '供货品类' }, { prop: 'ownerName', label: '联系人' }, { prop: 'planTime', label: '合作时间' }, { prop: 'status', label: '合作状态' }, { prop: 'remark', label: '供应商说明' }]
const formFields = [{ prop: 'recordNo', label: '供应商编号' }, { prop: 'recordName', label: '供应商名称' }, { prop: 'category', label: '供货品类' }, { prop: 'ownerName', label: '联系人' }, { prop: 'planTime', label: '合作时间' }, { prop: 'status', label: '合作状态', type: 'select' }, { prop: 'remark', label: '供应商说明', type: 'textarea' }]
</script>

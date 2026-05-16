<template><DataPage title="包装赋码" description="包装编号、废物名称、包装类型、交接人、封包时间和包装状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'HOSPITAL', 'COLLECTOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addWastePackage, deleteWastePackage, finishWastePackage, getWastePackagePage, processWastePackage, updateWastePackage } from '../api'
const api = { page: getWastePackagePage, add: addWastePackage, update: updateWastePackage, delete: deleteWastePackage, process: processWastePackage, finish: finishWastePackage }
const columns = [{ prop: 'recordNo', label: '包装编号' }, { prop: 'recordName', label: '废物名称' }, { prop: 'category', label: '包装类型' }, { prop: 'ownerName', label: '交接人' }, { prop: 'planTime', label: '封包时间' }, { prop: 'status', label: '包装状态' }, { prop: 'remark', label: '包装说明' }]
const formFields = [{ prop: 'recordNo', label: '包装编号' }, { prop: 'recordName', label: '废物名称' }, { prop: 'category', label: '包装类型' }, { prop: 'ownerName', label: '交接人' }, { prop: 'planTime', label: '封包时间' }, { prop: 'status', label: '包装状态', type: 'select' }, { prop: 'remark', label: '包装说明', type: 'textarea' }]
</script>

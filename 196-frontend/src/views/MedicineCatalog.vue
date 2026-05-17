<template><DataPage title="药品目录" description="药品编号、药品名称、药品类别、管理人员、入库时间和药品状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'PHARMACY', 'PHARMACIST', 'CLERK']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addMedicineCatalog, deleteMedicineCatalog, finishMedicineCatalog, getMedicineCatalogPage, processMedicineCatalog, updateMedicineCatalog } from '../api'
const api = { page: getMedicineCatalogPage, add: addMedicineCatalog, update: updateMedicineCatalog, delete: deleteMedicineCatalog, process: processMedicineCatalog, finish: finishMedicineCatalog }
const columns = [{ prop: 'recordNo', label: '药品编号' }, { prop: 'recordName', label: '药品名称' }, { prop: 'category', label: '药品类别' }, { prop: 'ownerName', label: '管理人员' }, { prop: 'planTime', label: '入库时间' }, { prop: 'status', label: '药品状态' }, { prop: 'remark', label: '药品说明' }]
const formFields = [{ prop: 'recordNo', label: '药品编号' }, { prop: 'recordName', label: '药品名称' }, { prop: 'category', label: '药品类别' }, { prop: 'ownerName', label: '管理人员' }, { prop: 'planTime', label: '入库时间' }, { prop: 'status', label: '药品状态', type: 'select' }, { prop: 'remark', label: '药品说明', type: 'textarea' }]
</script>

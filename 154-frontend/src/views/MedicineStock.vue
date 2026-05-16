<template><DataPage title="药品库存" description="药品编号、药品名称、药品类型、管理人、有效日期和库存状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'DOCTOR', 'NURSE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addMedicineStock, deleteMedicineStock, finishMedicineStock, getMedicineStockPage, processMedicineStock, updateMedicineStock } from '../api'
const api = { page: getMedicineStockPage, add: addMedicineStock, update: updateMedicineStock, delete: deleteMedicineStock, process: processMedicineStock, finish: finishMedicineStock }
const columns = [{ prop: 'recordNo', label: '药品编号' }, { prop: 'recordName', label: '药品名称' }, { prop: 'category', label: '药品类型' }, { prop: 'ownerName', label: '管理人' }, { prop: 'planTime', label: '有效日期' }, { prop: 'status', label: '库存状态' }, { prop: 'remark', label: '存储说明' }]
const formFields = [{ prop: 'recordNo', label: '药品编号' }, { prop: 'recordName', label: '药品名称' }, { prop: 'category', label: '药品类型' }, { prop: 'ownerName', label: '管理人' }, { prop: 'planTime', label: '有效日期' }, { prop: 'status', label: '库存状态', type: 'select' }, { prop: 'remark', label: '存储说明', type: 'textarea' }]
</script>

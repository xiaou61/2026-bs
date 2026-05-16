<template><DataPage title="治疗项目" description="项目编号、项目名称、治疗类型、负责医生、启用时间和项目状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'RECEPTION', 'DENTIST', 'CASHIER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addTreatmentCatalog, deleteTreatmentCatalog, finishTreatmentCatalog, getTreatmentCatalogPage, processTreatmentCatalog, updateTreatmentCatalog } from '../api'
const api = { page: getTreatmentCatalogPage, add: addTreatmentCatalog, update: updateTreatmentCatalog, delete: deleteTreatmentCatalog, process: processTreatmentCatalog, finish: finishTreatmentCatalog }
const columns = [{ prop: 'recordNo', label: '项目编号' }, { prop: 'recordName', label: '项目名称' }, { prop: 'category', label: '治疗类型' }, { prop: 'ownerName', label: '负责医生' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '项目状态' }, { prop: 'remark', label: '项目说明' }]
const formFields = [{ prop: 'recordNo', label: '项目编号' }, { prop: 'recordName', label: '项目名称' }, { prop: 'category', label: '治疗类型' }, { prop: 'ownerName', label: '负责医生' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '项目状态', type: 'select' }, { prop: 'remark', label: '项目说明', type: 'textarea' }]
</script>

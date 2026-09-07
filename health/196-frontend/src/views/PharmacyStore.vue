<template><DataPage title="药店门店" description="门店编号、门店名称、门店类型、负责人、建档时间和门店状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'PHARMACY']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addPharmacyStore, deletePharmacyStore, finishPharmacyStore, getPharmacyStorePage, processPharmacyStore, updatePharmacyStore } from '../api'
const api = { page: getPharmacyStorePage, add: addPharmacyStore, update: updatePharmacyStore, delete: deletePharmacyStore, process: processPharmacyStore, finish: finishPharmacyStore }
const columns = [{ prop: 'recordNo', label: '门店编号' }, { prop: 'recordName', label: '门店名称' }, { prop: 'category', label: '门店类型' }, { prop: 'ownerName', label: '负责人' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '门店状态' }, { prop: 'remark', label: '门店说明' }]
const formFields = [{ prop: 'recordNo', label: '门店编号' }, { prop: 'recordName', label: '门店名称' }, { prop: 'category', label: '门店类型' }, { prop: 'ownerName', label: '负责人' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '门店状态', type: 'select' }, { prop: 'remark', label: '门店说明', type: 'textarea' }]
</script>

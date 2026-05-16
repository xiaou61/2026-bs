<template><DataPage title="物资台账" description="物资编号、物资名称、物资类型、保管人、入库时间和物资状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'UNION', 'WAREHOUSE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addMaterialAsset, deleteMaterialAsset, finishMaterialAsset, getMaterialAssetPage, processMaterialAsset, updateMaterialAsset } from '../api'
const api = { page: getMaterialAssetPage, add: addMaterialAsset, update: updateMaterialAsset, delete: deleteMaterialAsset, process: processMaterialAsset, finish: finishMaterialAsset }
const columns = [{ prop: 'recordNo', label: '物资编号' }, { prop: 'recordName', label: '物资名称' }, { prop: 'category', label: '物资类型' }, { prop: 'ownerName', label: '保管人' }, { prop: 'planTime', label: '入库时间' }, { prop: 'status', label: '物资状态' }, { prop: 'remark', label: '物资说明' }]
const formFields = [{ prop: 'recordNo', label: '物资编号' }, { prop: 'recordName', label: '物资名称' }, { prop: 'category', label: '物资类型' }, { prop: 'ownerName', label: '保管人' }, { prop: 'planTime', label: '入库时间' }, { prop: 'status', label: '物资状态', type: 'select' }, { prop: 'remark', label: '物资说明', type: 'textarea' }]
</script>

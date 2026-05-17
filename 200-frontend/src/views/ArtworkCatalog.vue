<template><DataPage title="作品档案" description="作品编号、作品名称、作品类型、创作人员、入库时间和作品状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'WORKSHOP', 'CURATOR', 'SALES']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addArtworkCatalog, deleteArtworkCatalog, finishArtworkCatalog, getArtworkCatalogPage, processArtworkCatalog, updateArtworkCatalog } from '../api'
const api = { page: getArtworkCatalogPage, add: addArtworkCatalog, update: updateArtworkCatalog, delete: deleteArtworkCatalog, process: processArtworkCatalog, finish: finishArtworkCatalog }
const columns = [{ prop: 'recordNo', label: '作品编号' }, { prop: 'recordName', label: '作品名称' }, { prop: 'category', label: '作品类型' }, { prop: 'ownerName', label: '创作人员' }, { prop: 'planTime', label: '入库时间' }, { prop: 'status', label: '作品状态' }, { prop: 'remark', label: '作品说明' }]
const formFields = [{ prop: 'recordNo', label: '作品编号' }, { prop: 'recordName', label: '作品名称' }, { prop: 'category', label: '作品类型' }, { prop: 'ownerName', label: '创作人员' }, { prop: 'planTime', label: '入库时间' }, { prop: 'status', label: '作品状态', type: 'select' }, { prop: 'remark', label: '作品说明', type: 'textarea' }]
</script>

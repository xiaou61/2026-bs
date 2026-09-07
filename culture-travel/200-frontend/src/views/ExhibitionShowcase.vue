<template><DataPage title="作品展销" description="展销编号、作品名称、展销类型、展陈人员、上架时间和展销状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'WORKSHOP', 'CURATOR', 'SALES', 'VISITOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addExhibitionShowcase, deleteExhibitionShowcase, finishExhibitionShowcase, getExhibitionShowcasePage, processExhibitionShowcase, updateExhibitionShowcase } from '../api'
const api = { page: getExhibitionShowcasePage, add: addExhibitionShowcase, update: updateExhibitionShowcase, delete: deleteExhibitionShowcase, process: processExhibitionShowcase, finish: finishExhibitionShowcase }
const columns = [{ prop: 'recordNo', label: '展销编号' }, { prop: 'recordName', label: '作品名称' }, { prop: 'category', label: '展销类型' }, { prop: 'ownerName', label: '展陈人员' }, { prop: 'planTime', label: '上架时间' }, { prop: 'status', label: '展销状态' }, { prop: 'remark', label: '展销说明' }]
const formFields = [{ prop: 'recordNo', label: '展销编号' }, { prop: 'recordName', label: '作品名称' }, { prop: 'category', label: '展销类型' }, { prop: 'ownerName', label: '展陈人员' }, { prop: 'planTime', label: '上架时间' }, { prop: 'status', label: '展销状态', type: 'select' }, { prop: 'remark', label: '展销说明', type: 'textarea' }]
</script>

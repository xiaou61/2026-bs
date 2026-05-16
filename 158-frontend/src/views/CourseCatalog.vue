<template><DataPage title="课程产品" description="课程编号、课程名称、课程类型、负责人、上架时间和课程状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'PRINCIPAL', 'ACADEMIC', 'FINANCE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addCourseCatalog, deleteCourseCatalog, finishCourseCatalog, getCourseCatalogPage, processCourseCatalog, updateCourseCatalog } from '../api'
const api = { page: getCourseCatalogPage, add: addCourseCatalog, update: updateCourseCatalog, delete: deleteCourseCatalog, process: processCourseCatalog, finish: finishCourseCatalog }
const columns = [{ prop: 'recordNo', label: '课程编号' }, { prop: 'recordName', label: '课程名称' }, { prop: 'category', label: '课程类型' }, { prop: 'ownerName', label: '负责人' }, { prop: 'planTime', label: '上架时间' }, { prop: 'status', label: '课程状态' }, { prop: 'remark', label: '课程说明' }]
const formFields = [{ prop: 'recordNo', label: '课程编号' }, { prop: 'recordName', label: '课程名称' }, { prop: 'category', label: '课程类型' }, { prop: 'ownerName', label: '负责人' }, { prop: 'planTime', label: '上架时间' }, { prop: 'status', label: '课程状态', type: 'select' }, { prop: 'remark', label: '课程说明', type: 'textarea' }]
</script>

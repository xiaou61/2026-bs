<template><DataPage title="服务项目" description="项目编号、项目名称、服务类型、维护人员、启用时间和项目状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'AGENCY', 'DISPATCH', 'RESIDENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addServiceCatalog, deleteServiceCatalog, finishServiceCatalog, getServiceCatalogPage, processServiceCatalog, updateServiceCatalog } from '../api'
const api = { page: getServiceCatalogPage, add: addServiceCatalog, update: updateServiceCatalog, delete: deleteServiceCatalog, process: processServiceCatalog, finish: finishServiceCatalog }
const columns = [{ prop: 'recordNo', label: '项目编号' }, { prop: 'recordName', label: '项目名称' }, { prop: 'category', label: '服务类型' }, { prop: 'ownerName', label: '维护人员' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '项目状态' }, { prop: 'remark', label: '项目说明' }]
const formFields = [{ prop: 'recordNo', label: '项目编号' }, { prop: 'recordName', label: '项目名称' }, { prop: 'category', label: '服务类型' }, { prop: 'ownerName', label: '维护人员' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '项目状态', type: 'select' }, { prop: 'remark', label: '项目说明', type: 'textarea' }]
</script>

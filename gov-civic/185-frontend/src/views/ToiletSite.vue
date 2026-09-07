<template><DataPage title="公厕点位" description="点位编号、点位名称、区域类型、负责人员、启用时间和点位状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'SANITATION', 'INSPECTOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addToiletSite, deleteToiletSite, finishToiletSite, getToiletSitePage, processToiletSite, updateToiletSite } from '../api'
const api = { page: getToiletSitePage, add: addToiletSite, update: updateToiletSite, delete: deleteToiletSite, process: processToiletSite, finish: finishToiletSite }
const columns = [{ prop: 'recordNo', label: '点位编号' }, { prop: 'recordName', label: '点位名称' }, { prop: 'category', label: '区域类型' }, { prop: 'ownerName', label: '负责人员' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '点位状态' }, { prop: 'remark', label: '点位说明' }]
const formFields = [{ prop: 'recordNo', label: '点位编号' }, { prop: 'recordName', label: '点位名称' }, { prop: 'category', label: '区域类型' }, { prop: 'ownerName', label: '负责人员' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '点位状态', type: 'select' }, { prop: 'remark', label: '点位说明', type: 'textarea' }]
</script>

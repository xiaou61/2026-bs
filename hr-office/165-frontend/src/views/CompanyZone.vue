<template><DataPage title="楼宇区域" description="区域编号、区域名称、楼宇位置、区域负责人、开放时间和区域状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'RECEPTION', 'SECURITY', 'MANAGER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addCompanyZone, deleteCompanyZone, finishCompanyZone, getCompanyZonePage, processCompanyZone, updateCompanyZone } from '../api'
const api = { page: getCompanyZonePage, add: addCompanyZone, update: updateCompanyZone, delete: deleteCompanyZone, process: processCompanyZone, finish: finishCompanyZone }
const columns = [{ prop: 'recordNo', label: '区域编号' }, { prop: 'recordName', label: '区域名称' }, { prop: 'category', label: '楼宇位置' }, { prop: 'ownerName', label: '区域负责人' }, { prop: 'planTime', label: '开放时间' }, { prop: 'status', label: '区域状态' }, { prop: 'remark', label: '区域说明' }]
const formFields = [{ prop: 'recordNo', label: '区域编号' }, { prop: 'recordName', label: '区域名称' }, { prop: 'category', label: '楼宇位置' }, { prop: 'ownerName', label: '区域负责人' }, { prop: 'planTime', label: '开放时间' }, { prop: 'status', label: '区域状态', type: 'select' }, { prop: 'remark', label: '区域说明', type: 'textarea' }]
</script>

<template><DataPage title="市场区域" description="区域编号、区域名称、经营品类、区域负责人、开放时间和区域状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'MARKET', 'INSPECTOR', 'REGULATOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addMarketArea, deleteMarketArea, finishMarketArea, getMarketAreaPage, processMarketArea, updateMarketArea } from '../api'
const api = { page: getMarketAreaPage, add: addMarketArea, update: updateMarketArea, delete: deleteMarketArea, process: processMarketArea, finish: finishMarketArea }
const columns = [{ prop: 'recordNo', label: '区域编号' }, { prop: 'recordName', label: '区域名称' }, { prop: 'category', label: '经营品类' }, { prop: 'ownerName', label: '区域负责人' }, { prop: 'planTime', label: '开放时间' }, { prop: 'status', label: '区域状态' }, { prop: 'remark', label: '区域说明' }]
const formFields = [{ prop: 'recordNo', label: '区域编号' }, { prop: 'recordName', label: '区域名称' }, { prop: 'category', label: '经营品类' }, { prop: 'ownerName', label: '区域负责人' }, { prop: 'planTime', label: '开放时间' }, { prop: 'status', label: '区域状态', type: 'select' }, { prop: 'remark', label: '区域说明', type: 'textarea' }]
</script>

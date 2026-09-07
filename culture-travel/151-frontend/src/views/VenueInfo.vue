<template><DataPage title="场馆档案" description="场馆编码、场馆名称、开放区域、负责人、开放时间和状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'MANAGER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addVenueInfo, deleteVenueInfo, finishVenueInfo, getVenueInfoPage, processVenueInfo, updateVenueInfo } from '../api'
const api = { page: getVenueInfoPage, add: addVenueInfo, update: updateVenueInfo, delete: deleteVenueInfo, process: processVenueInfo, finish: finishVenueInfo }
const columns = [{ prop: 'recordNo', label: '场馆编码' }, { prop: 'recordName', label: '场馆名称' }, { prop: 'category', label: '开放区域' }, { prop: 'ownerName', label: '负责人' }, { prop: 'planTime', label: '开放时间' }, { prop: 'status', label: '开放状态' }, { prop: 'remark', label: '场馆说明' }]
const formFields = [{ prop: 'recordNo', label: '场馆编码' }, { prop: 'recordName', label: '场馆名称' }, { prop: 'category', label: '开放区域' }, { prop: 'ownerName', label: '负责人' }, { prop: 'planTime', label: '开放时间' }, { prop: 'status', label: '开放状态', type: 'select' }, { prop: 'remark', label: '场馆说明', type: 'textarea' }]
</script>

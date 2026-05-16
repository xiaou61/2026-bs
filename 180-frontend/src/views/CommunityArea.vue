<template><DataPage title="小区区域" description="区域编号、区域名称、区域类型、物业人员、启用时间和区域状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'PROPERTY', 'DISPATCH']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addCommunityArea, deleteCommunityArea, finishCommunityArea, getCommunityAreaPage, processCommunityArea, updateCommunityArea } from '../api'
const api = { page: getCommunityAreaPage, add: addCommunityArea, update: updateCommunityArea, delete: deleteCommunityArea, process: processCommunityArea, finish: finishCommunityArea }
const columns = [{ prop: 'recordNo', label: '区域编号' }, { prop: 'recordName', label: '区域名称' }, { prop: 'category', label: '区域类型' }, { prop: 'ownerName', label: '物业人员' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '区域状态' }, { prop: 'remark', label: '区域说明' }]
const formFields = [{ prop: 'recordNo', label: '区域编号' }, { prop: 'recordName', label: '区域名称' }, { prop: 'category', label: '区域类型' }, { prop: 'ownerName', label: '物业人员' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '区域状态', type: 'select' }, { prop: 'remark', label: '区域说明', type: 'textarea' }]
</script>

<template><DataPage title="社区区域" description="区域编号、区域名称、覆盖楼栋、区域负责人、启用时间和区域状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'COMMUNITY', 'SUPERVISOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addCommunityArea, deleteCommunityArea, finishCommunityArea, getCommunityAreaPage, processCommunityArea, updateCommunityArea } from '../api'
const api = { page: getCommunityAreaPage, add: addCommunityArea, update: updateCommunityArea, delete: deleteCommunityArea, process: processCommunityArea, finish: finishCommunityArea }
const columns = [{ prop: 'recordNo', label: '区域编号' }, { prop: 'recordName', label: '区域名称' }, { prop: 'category', label: '覆盖楼栋' }, { prop: 'ownerName', label: '区域负责人' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '区域状态' }, { prop: 'remark', label: '区域说明' }]
const formFields = [{ prop: 'recordNo', label: '区域编号' }, { prop: 'recordName', label: '区域名称' }, { prop: 'category', label: '覆盖楼栋' }, { prop: 'ownerName', label: '区域负责人' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '区域状态', type: 'select' }, { prop: 'remark', label: '区域说明', type: 'textarea' }]
</script>

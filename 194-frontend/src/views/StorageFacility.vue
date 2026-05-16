<template><DataPage title="暂存设施" description="设施编号、设施名称、设施类型、管理人员、启用时间和设施状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'PARK', 'WAREHOUSE', 'INSPECTOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addStorageFacility, deleteStorageFacility, finishStorageFacility, getStorageFacilityPage, processStorageFacility, updateStorageFacility } from '../api'
const api = { page: getStorageFacilityPage, add: addStorageFacility, update: updateStorageFacility, delete: deleteStorageFacility, process: processStorageFacility, finish: finishStorageFacility }
const columns = [{ prop: 'recordNo', label: '设施编号' }, { prop: 'recordName', label: '设施名称' }, { prop: 'category', label: '设施类型' }, { prop: 'ownerName', label: '管理人员' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '设施状态' }, { prop: 'remark', label: '设施说明' }]
const formFields = [{ prop: 'recordNo', label: '设施编号' }, { prop: 'recordName', label: '设施名称' }, { prop: 'category', label: '设施类型' }, { prop: 'ownerName', label: '管理人员' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '设施状态', type: 'select' }, { prop: 'remark', label: '设施说明', type: 'textarea' }]
</script>

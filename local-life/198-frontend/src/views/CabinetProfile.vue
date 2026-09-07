<template><DataPage title="设备柜档案" description="设备柜编号、点位名称、设备类型、维护人员、投放时间和设备柜状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'OPERATOR', 'SITE', 'INSPECTOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addCabinetProfile, deleteCabinetProfile, finishCabinetProfile, getCabinetProfilePage, processCabinetProfile, updateCabinetProfile } from '../api'
const api = { page: getCabinetProfilePage, add: addCabinetProfile, update: updateCabinetProfile, delete: deleteCabinetProfile, process: processCabinetProfile, finish: finishCabinetProfile }
const columns = [{ prop: 'recordNo', label: '设备柜编号' }, { prop: 'recordName', label: '点位名称' }, { prop: 'category', label: '设备类型' }, { prop: 'ownerName', label: '维护人员' }, { prop: 'planTime', label: '投放时间' }, { prop: 'status', label: '设备柜状态' }, { prop: 'remark', label: '设备柜说明' }]
const formFields = [{ prop: 'recordNo', label: '设备柜编号' }, { prop: 'recordName', label: '点位名称' }, { prop: 'category', label: '设备类型' }, { prop: 'ownerName', label: '维护人员' }, { prop: 'planTime', label: '投放时间' }, { prop: 'status', label: '设备柜状态', type: 'select' }, { prop: 'remark', label: '设备柜说明', type: 'textarea' }]
</script>

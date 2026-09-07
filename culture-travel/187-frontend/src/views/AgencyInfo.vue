<template><DataPage title="旅行社档案" description="旅行社编号、旅行社名称、服务类型、负责人员、合作时间和旅行社状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'AGENCY']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addAgencyInfo, deleteAgencyInfo, finishAgencyInfo, getAgencyInfoPage, processAgencyInfo, updateAgencyInfo } from '../api'
const api = { page: getAgencyInfoPage, add: addAgencyInfo, update: updateAgencyInfo, delete: deleteAgencyInfo, process: processAgencyInfo, finish: finishAgencyInfo }
const columns = [{ prop: 'recordNo', label: '旅行社编号' }, { prop: 'recordName', label: '旅行社名称' }, { prop: 'category', label: '服务类型' }, { prop: 'ownerName', label: '负责人员' }, { prop: 'planTime', label: '合作时间' }, { prop: 'status', label: '旅行社状态' }, { prop: 'remark', label: '旅行社说明' }]
const formFields = [{ prop: 'recordNo', label: '旅行社编号' }, { prop: 'recordName', label: '旅行社名称' }, { prop: 'category', label: '服务类型' }, { prop: 'ownerName', label: '负责人员' }, { prop: 'planTime', label: '合作时间' }, { prop: 'status', label: '旅行社状态', type: 'select' }, { prop: 'remark', label: '旅行社说明', type: 'textarea' }]
</script>

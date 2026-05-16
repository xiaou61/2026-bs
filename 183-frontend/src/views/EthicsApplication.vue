<template><DataPage title="伦理申请" description="申请编号、课题名称、实验类型、申请人员、申请时间和申请状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'LAB', 'RESEARCHER', 'ETHICS']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addEthicsApplication, deleteEthicsApplication, finishEthicsApplication, getEthicsApplicationPage, processEthicsApplication, updateEthicsApplication } from '../api'
const api = { page: getEthicsApplicationPage, add: addEthicsApplication, update: updateEthicsApplication, delete: deleteEthicsApplication, process: processEthicsApplication, finish: finishEthicsApplication }
const columns = [{ prop: 'recordNo', label: '申请编号' }, { prop: 'recordName', label: '课题名称' }, { prop: 'category', label: '实验类型' }, { prop: 'ownerName', label: '申请人员' }, { prop: 'planTime', label: '申请时间' }, { prop: 'status', label: '申请状态' }, { prop: 'remark', label: '申请说明' }]
const formFields = [{ prop: 'recordNo', label: '申请编号' }, { prop: 'recordName', label: '课题名称' }, { prop: 'category', label: '实验类型' }, { prop: 'ownerName', label: '申请人员' }, { prop: 'planTime', label: '申请时间' }, { prop: 'status', label: '申请状态', type: 'select' }, { prop: 'remark', label: '申请说明', type: 'textarea' }]
</script>

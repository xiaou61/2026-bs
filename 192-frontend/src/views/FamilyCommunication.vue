<template><DataPage title="家属沟通" description="沟通编号、服务患者、沟通类型、沟通人员、沟通时间和沟通状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'HOSPITAL', 'COORDINATOR', 'FAMILY']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addFamilyCommunication, deleteFamilyCommunication, finishFamilyCommunication, getFamilyCommunicationPage, processFamilyCommunication, updateFamilyCommunication } from '../api'
const api = { page: getFamilyCommunicationPage, add: addFamilyCommunication, update: updateFamilyCommunication, delete: deleteFamilyCommunication, process: processFamilyCommunication, finish: finishFamilyCommunication }
const columns = [{ prop: 'recordNo', label: '沟通编号' }, { prop: 'recordName', label: '服务患者' }, { prop: 'category', label: '沟通类型' }, { prop: 'ownerName', label: '沟通人员' }, { prop: 'planTime', label: '沟通时间' }, { prop: 'status', label: '沟通状态' }, { prop: 'remark', label: '沟通说明' }]
const formFields = [{ prop: 'recordNo', label: '沟通编号' }, { prop: 'recordName', label: '服务患者' }, { prop: 'category', label: '沟通类型' }, { prop: 'ownerName', label: '沟通人员' }, { prop: 'planTime', label: '沟通时间' }, { prop: 'status', label: '沟通状态', type: 'select' }, { prop: 'remark', label: '沟通说明', type: 'textarea' }]
</script>

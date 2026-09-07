<template><DataPage title="顾客档案" description="档案编号、顾客姓名、慢病类型、联系人员、建档时间和档案状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'PHARMACY', 'PHARMACIST', 'FOLLOWUP', 'CUSTOMER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addCustomerProfile, deleteCustomerProfile, finishCustomerProfile, getCustomerProfilePage, processCustomerProfile, updateCustomerProfile } from '../api'
const api = { page: getCustomerProfilePage, add: addCustomerProfile, update: updateCustomerProfile, delete: deleteCustomerProfile, process: processCustomerProfile, finish: finishCustomerProfile }
const columns = [{ prop: 'recordNo', label: '档案编号' }, { prop: 'recordName', label: '顾客姓名' }, { prop: 'category', label: '慢病类型' }, { prop: 'ownerName', label: '联系人员' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '档案状态' }, { prop: 'remark', label: '档案说明' }]
const formFields = [{ prop: 'recordNo', label: '档案编号' }, { prop: 'recordName', label: '顾客姓名' }, { prop: 'category', label: '慢病类型' }, { prop: 'ownerName', label: '联系人员' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '档案状态', type: 'select' }, { prop: 'remark', label: '档案说明', type: 'textarea' }]
</script>

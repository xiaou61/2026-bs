<template><DataPage title="月租合同" description="合同编号、月租车主、合同类型、经办人员、签约时间和合同状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'PARKING', 'FINANCE', 'TENANT', 'SERVICE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addLeaseContract, deleteLeaseContract, finishLeaseContract, getLeaseContractPage, processLeaseContract, updateLeaseContract } from '../api'
const api = { page: getLeaseContractPage, add: addLeaseContract, update: updateLeaseContract, delete: deleteLeaseContract, process: processLeaseContract, finish: finishLeaseContract }
const columns = [{ prop: 'recordNo', label: '合同编号' }, { prop: 'recordName', label: '月租车主' }, { prop: 'category', label: '合同类型' }, { prop: 'ownerName', label: '经办人员' }, { prop: 'planTime', label: '签约时间' }, { prop: 'status', label: '合同状态' }, { prop: 'remark', label: '合同说明' }]
const formFields = [{ prop: 'recordNo', label: '合同编号' }, { prop: 'recordName', label: '月租车主' }, { prop: 'category', label: '合同类型' }, { prop: 'ownerName', label: '经办人员' }, { prop: 'planTime', label: '签约时间' }, { prop: 'status', label: '合同状态', type: 'select' }, { prop: 'remark', label: '合同说明', type: 'textarea' }]
</script>

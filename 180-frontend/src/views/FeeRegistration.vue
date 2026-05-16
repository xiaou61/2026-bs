<template><DataPage title="费用登记" description="费用编号、维修工单、费用类型、登记人员、登记时间和费用状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'PROPERTY', 'FINANCE', 'WORKER', 'OWNER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addFeeRegistration, deleteFeeRegistration, finishFeeRegistration, getFeeRegistrationPage, processFeeRegistration, updateFeeRegistration } from '../api'
const api = { page: getFeeRegistrationPage, add: addFeeRegistration, update: updateFeeRegistration, delete: deleteFeeRegistration, process: processFeeRegistration, finish: finishFeeRegistration }
const columns = [{ prop: 'recordNo', label: '费用编号' }, { prop: 'recordName', label: '维修工单' }, { prop: 'category', label: '费用类型' }, { prop: 'ownerName', label: '登记人员' }, { prop: 'planTime', label: '登记时间' }, { prop: 'status', label: '费用状态' }, { prop: 'remark', label: '费用说明' }]
const formFields = [{ prop: 'recordNo', label: '费用编号' }, { prop: 'recordName', label: '维修工单' }, { prop: 'category', label: '费用类型' }, { prop: 'ownerName', label: '登记人员' }, { prop: 'planTime', label: '登记时间' }, { prop: 'status', label: '费用状态', type: 'select' }, { prop: 'remark', label: '费用说明', type: 'textarea' }]
</script>

<template><DataPage title="实验登记" description="实验编号、伦理申请、实验类型、研究人员、实验时间和实验状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'LAB', 'RESEARCHER', 'ETHICS']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addExperimentRegistration, deleteExperimentRegistration, finishExperimentRegistration, getExperimentRegistrationPage, processExperimentRegistration, updateExperimentRegistration } from '../api'
const api = { page: getExperimentRegistrationPage, add: addExperimentRegistration, update: updateExperimentRegistration, delete: deleteExperimentRegistration, process: processExperimentRegistration, finish: finishExperimentRegistration }
const columns = [{ prop: 'recordNo', label: '实验编号' }, { prop: 'recordName', label: '伦理申请' }, { prop: 'category', label: '实验类型' }, { prop: 'ownerName', label: '研究人员' }, { prop: 'planTime', label: '实验时间' }, { prop: 'status', label: '实验状态' }, { prop: 'remark', label: '实验说明' }]
const formFields = [{ prop: 'recordNo', label: '实验编号' }, { prop: 'recordName', label: '伦理申请' }, { prop: 'category', label: '实验类型' }, { prop: 'ownerName', label: '研究人员' }, { prop: 'planTime', label: '实验时间' }, { prop: 'status', label: '实验状态', type: 'select' }, { prop: 'remark', label: '实验说明', type: 'textarea' }]
</script>

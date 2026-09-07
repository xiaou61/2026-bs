<template><DataPage title="被访员工" description="工号、员工姓名、所属部门、接待权限、在岗时间和员工状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'RECEPTION', 'EMPLOYEE', 'MANAGER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addHostEmployee, deleteHostEmployee, finishHostEmployee, getHostEmployeePage, processHostEmployee, updateHostEmployee } from '../api'
const api = { page: getHostEmployeePage, add: addHostEmployee, update: updateHostEmployee, delete: deleteHostEmployee, process: processHostEmployee, finish: finishHostEmployee }
const columns = [{ prop: 'recordNo', label: '员工工号' }, { prop: 'recordName', label: '员工姓名' }, { prop: 'category', label: '所属部门' }, { prop: 'ownerName', label: '接待权限' }, { prop: 'planTime', label: '在岗时间' }, { prop: 'status', label: '员工状态' }, { prop: 'remark', label: '员工说明' }]
const formFields = [{ prop: 'recordNo', label: '员工工号' }, { prop: 'recordName', label: '员工姓名' }, { prop: 'category', label: '所属部门' }, { prop: 'ownerName', label: '接待权限' }, { prop: 'planTime', label: '在岗时间' }, { prop: 'status', label: '员工状态', type: 'select' }, { prop: 'remark', label: '员工说明', type: 'textarea' }]
</script>

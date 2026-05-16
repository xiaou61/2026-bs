<template><DataPage title="餐厅档案" description="餐厅编号、餐厅名称、经营类型、负责人员、启用时间和餐厅状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CANTEEN', 'SCHOOL']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addCanteenInfo, deleteCanteenInfo, finishCanteenInfo, getCanteenInfoPage, processCanteenInfo, updateCanteenInfo } from '../api'
const api = { page: getCanteenInfoPage, add: addCanteenInfo, update: updateCanteenInfo, delete: deleteCanteenInfo, process: processCanteenInfo, finish: finishCanteenInfo }
const columns = [{ prop: 'recordNo', label: '餐厅编号' }, { prop: 'recordName', label: '餐厅名称' }, { prop: 'category', label: '经营类型' }, { prop: 'ownerName', label: '负责人员' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '餐厅状态' }, { prop: 'remark', label: '餐厅说明' }]
const formFields = [{ prop: 'recordNo', label: '餐厅编号' }, { prop: 'recordName', label: '餐厅名称' }, { prop: 'category', label: '经营类型' }, { prop: 'ownerName', label: '负责人员' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '餐厅状态', type: 'select' }, { prop: 'remark', label: '餐厅说明', type: 'textarea' }]
</script>

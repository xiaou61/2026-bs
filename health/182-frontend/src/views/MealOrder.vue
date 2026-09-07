<template><DataPage title="助餐订单" description="订单编号、订餐老人、套餐类型、服务人员、下单时间和订单状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CENTER', 'ELDER', 'DISPATCH']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addMealOrder, deleteMealOrder, finishMealOrder, getMealOrderPage, processMealOrder, updateMealOrder } from '../api'
const api = { page: getMealOrderPage, add: addMealOrder, update: updateMealOrder, delete: deleteMealOrder, process: processMealOrder, finish: finishMealOrder }
const columns = [{ prop: 'recordNo', label: '订单编号' }, { prop: 'recordName', label: '订餐老人' }, { prop: 'category', label: '套餐类型' }, { prop: 'ownerName', label: '服务人员' }, { prop: 'planTime', label: '下单时间' }, { prop: 'status', label: '订单状态' }, { prop: 'remark', label: '订单说明' }]
const formFields = [{ prop: 'recordNo', label: '订单编号' }, { prop: 'recordName', label: '订餐老人' }, { prop: 'category', label: '套餐类型' }, { prop: 'ownerName', label: '服务人员' }, { prop: 'planTime', label: '下单时间' }, { prop: 'status', label: '订单状态', type: 'select' }, { prop: 'remark', label: '订单说明', type: 'textarea' }]
</script>

<template><DataPage title="菜品档案" description="菜品编号、菜品名称、菜品类型、厨师人员、上架时间和菜品状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CANTEEN', 'CHEF', 'SCHOOL']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addDishMenu, deleteDishMenu, finishDishMenu, getDishMenuPage, processDishMenu, updateDishMenu } from '../api'
const api = { page: getDishMenuPage, add: addDishMenu, update: updateDishMenu, delete: deleteDishMenu, process: processDishMenu, finish: finishDishMenu }
const columns = [{ prop: 'recordNo', label: '菜品编号' }, { prop: 'recordName', label: '菜品名称' }, { prop: 'category', label: '菜品类型' }, { prop: 'ownerName', label: '厨师人员' }, { prop: 'planTime', label: '上架时间' }, { prop: 'status', label: '菜品状态' }, { prop: 'remark', label: '菜品说明' }]
const formFields = [{ prop: 'recordNo', label: '菜品编号' }, { prop: 'recordName', label: '菜品名称' }, { prop: 'category', label: '菜品类型' }, { prop: 'ownerName', label: '厨师人员' }, { prop: 'planTime', label: '上架时间' }, { prop: 'status', label: '菜品状态', type: 'select' }, { prop: 'remark', label: '菜品说明', type: 'textarea' }]
</script>

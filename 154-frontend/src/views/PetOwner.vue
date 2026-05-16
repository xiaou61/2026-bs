<template><DataPage title="宠主档案" description="宠主编号、宠主姓名、联系方式、常住区域、建档时间和账号状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'RECEPTION', 'OWNER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addPetOwner, deletePetOwner, finishPetOwner, getPetOwnerPage, processPetOwner, updatePetOwner } from '../api'
const api = { page: getPetOwnerPage, add: addPetOwner, update: updatePetOwner, delete: deletePetOwner, process: processPetOwner, finish: finishPetOwner }
const columns = [{ prop: 'recordNo', label: '宠主编号' }, { prop: 'recordName', label: '宠主姓名' }, { prop: 'category', label: '联系方式' }, { prop: 'ownerName', label: '常住区域' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '账号状态' }, { prop: 'remark', label: '备注信息' }]
const formFields = [{ prop: 'recordNo', label: '宠主编号' }, { prop: 'recordName', label: '宠主姓名' }, { prop: 'category', label: '联系方式' }, { prop: 'ownerName', label: '常住区域' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '账号状态', type: 'select' }, { prop: 'remark', label: '备注信息', type: 'textarea' }]
</script>

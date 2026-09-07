<template><DataPage title="宠物档案" description="宠物编号、宠物昵称、宠物类型、宠主姓名、出生日期和健康状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'RECEPTION', 'DOCTOR', 'OWNER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addPetProfile, deletePetProfile, finishPetProfile, getPetProfilePage, processPetProfile, updatePetProfile } from '../api'
const api = { page: getPetProfilePage, add: addPetProfile, update: updatePetProfile, delete: deletePetProfile, process: processPetProfile, finish: finishPetProfile }
const columns = [{ prop: 'recordNo', label: '宠物编号' }, { prop: 'recordName', label: '宠物昵称' }, { prop: 'category', label: '宠物类型' }, { prop: 'ownerName', label: '宠主姓名' }, { prop: 'planTime', label: '出生日期' }, { prop: 'status', label: '健康状态' }, { prop: 'remark', label: '过敏备注' }]
const formFields = [{ prop: 'recordNo', label: '宠物编号' }, { prop: 'recordName', label: '宠物昵称' }, { prop: 'category', label: '宠物类型' }, { prop: 'ownerName', label: '宠主姓名' }, { prop: 'planTime', label: '出生日期' }, { prop: 'status', label: '健康状态', type: 'select' }, { prop: 'remark', label: '过敏备注', type: 'textarea' }]
</script>

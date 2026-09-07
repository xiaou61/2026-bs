<template><DataPage title="实验动物档案" description="动物编号、动物品系、动物类型、负责人员、建档时间和档案状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'LAB', 'BREEDER', 'RESEARCHER', 'VET']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addAnimalProfile, deleteAnimalProfile, finishAnimalProfile, getAnimalProfilePage, processAnimalProfile, updateAnimalProfile } from '../api'
const api = { page: getAnimalProfilePage, add: addAnimalProfile, update: updateAnimalProfile, delete: deleteAnimalProfile, process: processAnimalProfile, finish: finishAnimalProfile }
const columns = [{ prop: 'recordNo', label: '动物编号' }, { prop: 'recordName', label: '动物品系' }, { prop: 'category', label: '动物类型' }, { prop: 'ownerName', label: '负责人员' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '档案状态' }, { prop: 'remark', label: '档案说明' }]
const formFields = [{ prop: 'recordNo', label: '动物编号' }, { prop: 'recordName', label: '动物品系' }, { prop: 'category', label: '动物类型' }, { prop: 'ownerName', label: '负责人员' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '档案状态', type: 'select' }, { prop: 'remark', label: '档案说明', type: 'textarea' }]
</script>

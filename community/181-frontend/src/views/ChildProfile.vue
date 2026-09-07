<template><DataPage title="儿童档案" description="儿童编号、儿童姓名、托管类型、监护家长、入托时间和档案状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CENTER', 'TEACHER', 'PARENT', 'NURSE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addChildProfile, deleteChildProfile, finishChildProfile, getChildProfilePage, processChildProfile, updateChildProfile } from '../api'
const api = { page: getChildProfilePage, add: addChildProfile, update: updateChildProfile, delete: deleteChildProfile, process: processChildProfile, finish: finishChildProfile }
const columns = [{ prop: 'recordNo', label: '儿童编号' }, { prop: 'recordName', label: '儿童姓名' }, { prop: 'category', label: '托管类型' }, { prop: 'ownerName', label: '监护家长' }, { prop: 'planTime', label: '入托时间' }, { prop: 'status', label: '档案状态' }, { prop: 'remark', label: '档案说明' }]
const formFields = [{ prop: 'recordNo', label: '儿童编号' }, { prop: 'recordName', label: '儿童姓名' }, { prop: 'category', label: '托管类型' }, { prop: 'ownerName', label: '监护家长' }, { prop: 'planTime', label: '入托时间' }, { prop: 'status', label: '档案状态', type: 'select' }, { prop: 'remark', label: '档案说明', type: 'textarea' }]
</script>

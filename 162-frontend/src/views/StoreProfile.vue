<template><DataPage title="门店档案" description="门店编号、门店名称、经营类型、店长、启用时间和门店状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'MANAGER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addStoreProfile, deleteStoreProfile, finishStoreProfile, getStoreProfilePage, processStoreProfile, updateStoreProfile } from '../api'
const api = { page: getStoreProfilePage, add: addStoreProfile, update: updateStoreProfile, delete: deleteStoreProfile, process: processStoreProfile, finish: finishStoreProfile }
const columns = [{ prop: 'recordNo', label: '门店编号' }, { prop: 'recordName', label: '门店名称' }, { prop: 'category', label: '经营类型' }, { prop: 'ownerName', label: '店长' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '门店状态' }, { prop: 'remark', label: '门店说明' }]
const formFields = [{ prop: 'recordNo', label: '门店编号' }, { prop: 'recordName', label: '门店名称' }, { prop: 'category', label: '经营类型' }, { prop: 'ownerName', label: '店长' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '门店状态', type: 'select' }, { prop: 'remark', label: '门店说明', type: 'textarea' }]
</script>

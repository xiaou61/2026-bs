<template><DataPage title="主播档案" description="主播编号、主播姓名、主播类型、运营人员、签约时间和主播状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'BASE', 'ANCHOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addAnchorProfile, deleteAnchorProfile, finishAnchorProfile, getAnchorProfilePage, processAnchorProfile, updateAnchorProfile } from '../api'
const api = { page: getAnchorProfilePage, add: addAnchorProfile, update: updateAnchorProfile, delete: deleteAnchorProfile, process: processAnchorProfile, finish: finishAnchorProfile }
const columns = [{ prop: 'recordNo', label: '主播编号' }, { prop: 'recordName', label: '主播姓名' }, { prop: 'category', label: '主播类型' }, { prop: 'ownerName', label: '运营人员' }, { prop: 'planTime', label: '签约时间' }, { prop: 'status', label: '主播状态' }, { prop: 'remark', label: '主播说明' }]
const formFields = [{ prop: 'recordNo', label: '主播编号' }, { prop: 'recordName', label: '主播姓名' }, { prop: 'category', label: '主播类型' }, { prop: 'ownerName', label: '运营人员' }, { prop: 'planTime', label: '签约时间' }, { prop: 'status', label: '主播状态', type: 'select' }, { prop: 'remark', label: '主播说明', type: 'textarea' }]
</script>

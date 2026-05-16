<template><DataPage title="宿舍楼栋" description="楼栋编号、楼栋名称、楼栋类型、管理员、启用时间和楼栋状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'LOGISTICS']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addDormBuilding, deleteDormBuilding, finishDormBuilding, getDormBuildingPage, processDormBuilding, updateDormBuilding } from '../api'
const api = { page: getDormBuildingPage, add: addDormBuilding, update: updateDormBuilding, delete: deleteDormBuilding, process: processDormBuilding, finish: finishDormBuilding }
const columns = [{ prop: 'recordNo', label: '楼栋编号' }, { prop: 'recordName', label: '楼栋名称' }, { prop: 'category', label: '楼栋类型' }, { prop: 'ownerName', label: '管理员' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '楼栋状态' }, { prop: 'remark', label: '楼栋说明' }]
const formFields = [{ prop: 'recordNo', label: '楼栋编号' }, { prop: 'recordName', label: '楼栋名称' }, { prop: 'category', label: '楼栋类型' }, { prop: 'ownerName', label: '管理员' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '楼栋状态', type: 'select' }, { prop: 'remark', label: '楼栋说明', type: 'textarea' }]
</script>

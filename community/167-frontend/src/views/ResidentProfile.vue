<template><DataPage title="居民档案" description="居民编号、居民姓名、所属楼栋、联系方式、登记时间和居民状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'COMMUNITY', 'SUPERVISOR', 'RESIDENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addResidentProfile, deleteResidentProfile, finishResidentProfile, getResidentProfilePage, processResidentProfile, updateResidentProfile } from '../api'
const api = { page: getResidentProfilePage, add: addResidentProfile, update: updateResidentProfile, delete: deleteResidentProfile, process: processResidentProfile, finish: finishResidentProfile }
const columns = [{ prop: 'recordNo', label: '居民编号' }, { prop: 'recordName', label: '居民姓名' }, { prop: 'category', label: '所属楼栋' }, { prop: 'ownerName', label: '联系方式' }, { prop: 'planTime', label: '登记时间' }, { prop: 'status', label: '居民状态' }, { prop: 'remark', label: '居民说明' }]
const formFields = [{ prop: 'recordNo', label: '居民编号' }, { prop: 'recordName', label: '居民姓名' }, { prop: 'category', label: '所属楼栋' }, { prop: 'ownerName', label: '联系方式' }, { prop: 'planTime', label: '登记时间' }, { prop: 'status', label: '居民状态', type: 'select' }, { prop: 'remark', label: '居民说明', type: 'textarea' }]
</script>

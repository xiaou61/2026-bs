<template><DataPage title="楼宇档案" description="楼宇编号、楼宇名称、楼层区域、物业负责人、建档时间和楼宇状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'PROPERTY', 'INSPECTOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addBuildingProfile, deleteBuildingProfile, finishBuildingProfile, getBuildingProfilePage, processBuildingProfile, updateBuildingProfile } from '../api'
const api = { page: getBuildingProfilePage, add: addBuildingProfile, update: updateBuildingProfile, delete: deleteBuildingProfile, process: processBuildingProfile, finish: finishBuildingProfile }
const columns = [{ prop: 'recordNo', label: '楼宇编号' }, { prop: 'recordName', label: '楼宇名称' }, { prop: 'category', label: '楼层区域' }, { prop: 'ownerName', label: '物业负责人' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '楼宇状态' }, { prop: 'remark', label: '楼宇说明' }]
const formFields = [{ prop: 'recordNo', label: '楼宇编号' }, { prop: 'recordName', label: '楼宇名称' }, { prop: 'category', label: '楼层区域' }, { prop: 'ownerName', label: '物业负责人' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '楼宇状态', type: 'select' }, { prop: 'remark', label: '楼宇说明', type: 'textarea' }]
</script>

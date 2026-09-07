<template><DataPage title="投放点位" description="点位编号、点位名称、点位类型、负责人、建档时间和点位状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'OPERATOR', 'SITE', 'MERCHANT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addPlacementSite, deletePlacementSite, finishPlacementSite, getPlacementSitePage, processPlacementSite, updatePlacementSite } from '../api'
const api = { page: getPlacementSitePage, add: addPlacementSite, update: updatePlacementSite, delete: deletePlacementSite, process: processPlacementSite, finish: finishPlacementSite }
const columns = [{ prop: 'recordNo', label: '点位编号' }, { prop: 'recordName', label: '点位名称' }, { prop: 'category', label: '点位类型' }, { prop: 'ownerName', label: '负责人' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '点位状态' }, { prop: 'remark', label: '点位说明' }]
const formFields = [{ prop: 'recordNo', label: '点位编号' }, { prop: 'recordName', label: '点位名称' }, { prop: 'category', label: '点位类型' }, { prop: 'ownerName', label: '负责人' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '点位状态', type: 'select' }, { prop: 'remark', label: '点位说明', type: 'textarea' }]
</script>

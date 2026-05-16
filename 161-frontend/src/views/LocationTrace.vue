<template><DataPage title="位置追踪" description="追踪编号、关联物品、追踪类型、巡查人、定位时间和追踪状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'SECURITY', 'SCENIC', 'SERVICE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addLocationTrace, deleteLocationTrace, finishLocationTrace, getLocationTracePage, processLocationTrace, updateLocationTrace } from '../api'
const api = { page: getLocationTracePage, add: addLocationTrace, update: updateLocationTrace, delete: deleteLocationTrace, process: processLocationTrace, finish: finishLocationTrace }
const columns = [{ prop: 'recordNo', label: '追踪编号' }, { prop: 'recordName', label: '关联物品' }, { prop: 'category', label: '追踪类型' }, { prop: 'ownerName', label: '巡查人' }, { prop: 'planTime', label: '定位时间' }, { prop: 'status', label: '追踪状态' }, { prop: 'remark', label: '追踪说明' }]
const formFields = [{ prop: 'recordNo', label: '追踪编号' }, { prop: 'recordName', label: '关联物品' }, { prop: 'category', label: '追踪类型' }, { prop: 'ownerName', label: '巡查人' }, { prop: 'planTime', label: '定位时间' }, { prop: 'status', label: '追踪状态', type: 'select' }, { prop: 'remark', label: '追踪说明', type: 'textarea' }]
</script>

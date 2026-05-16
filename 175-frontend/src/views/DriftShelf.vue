<template><DataPage title="漂流书架" description="书架编号、书架名称、所在校区、维护人员、启用时间和书架状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'LIBRARY', 'CURATOR', 'CLUB']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addDriftShelf, deleteDriftShelf, finishDriftShelf, getDriftShelfPage, processDriftShelf, updateDriftShelf } from '../api'
const api = { page: getDriftShelfPage, add: addDriftShelf, update: updateDriftShelf, delete: deleteDriftShelf, process: processDriftShelf, finish: finishDriftShelf }
const columns = [{ prop: 'recordNo', label: '书架编号' }, { prop: 'recordName', label: '书架名称' }, { prop: 'category', label: '所在校区' }, { prop: 'ownerName', label: '维护人员' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '书架状态' }, { prop: 'remark', label: '书架说明' }]
const formFields = [{ prop: 'recordNo', label: '书架编号' }, { prop: 'recordName', label: '书架名称' }, { prop: 'category', label: '所在校区' }, { prop: 'ownerName', label: '维护人员' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '书架状态', type: 'select' }, { prop: 'remark', label: '书架说明', type: 'textarea' }]
</script>

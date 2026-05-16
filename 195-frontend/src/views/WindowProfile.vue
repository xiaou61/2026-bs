<template><DataPage title="窗口档案" description="窗口编号、窗口名称、业务类型、窗口负责人、开放时间和窗口状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CENTER', 'WINDOW']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addWindowProfile, deleteWindowProfile, finishWindowProfile, getWindowProfilePage, processWindowProfile, updateWindowProfile } from '../api'
const api = { page: getWindowProfilePage, add: addWindowProfile, update: updateWindowProfile, delete: deleteWindowProfile, process: processWindowProfile, finish: finishWindowProfile }
const columns = [{ prop: 'recordNo', label: '窗口编号' }, { prop: 'recordName', label: '窗口名称' }, { prop: 'category', label: '业务类型' }, { prop: 'ownerName', label: '窗口负责人' }, { prop: 'planTime', label: '开放时间' }, { prop: 'status', label: '窗口状态' }, { prop: 'remark', label: '窗口说明' }]
const formFields = [{ prop: 'recordNo', label: '窗口编号' }, { prop: 'recordName', label: '窗口名称' }, { prop: 'category', label: '业务类型' }, { prop: 'ownerName', label: '窗口负责人' }, { prop: 'planTime', label: '开放时间' }, { prop: 'status', label: '窗口状态', type: 'select' }, { prop: 'remark', label: '窗口说明', type: 'textarea' }]
</script>

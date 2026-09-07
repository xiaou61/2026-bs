<template><DataPage title="游客认领" description="认领编号、认领物品、认领类型、申请人、申请时间和认领状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'SERVICE', 'VISITOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addClaimApplication, deleteClaimApplication, finishClaimApplication, getClaimApplicationPage, processClaimApplication, updateClaimApplication } from '../api'
const api = { page: getClaimApplicationPage, add: addClaimApplication, update: updateClaimApplication, delete: deleteClaimApplication, process: processClaimApplication, finish: finishClaimApplication }
const columns = [{ prop: 'recordNo', label: '认领编号' }, { prop: 'recordName', label: '认领物品' }, { prop: 'category', label: '认领类型' }, { prop: 'ownerName', label: '申请人' }, { prop: 'planTime', label: '申请时间' }, { prop: 'status', label: '认领状态' }, { prop: 'remark', label: '认领说明' }]
const formFields = [{ prop: 'recordNo', label: '认领编号' }, { prop: 'recordName', label: '认领物品' }, { prop: 'category', label: '认领类型' }, { prop: 'ownerName', label: '申请人' }, { prop: 'planTime', label: '申请时间' }, { prop: 'status', label: '认领状态', type: 'select' }, { prop: 'remark', label: '认领说明', type: 'textarea' }]
</script>

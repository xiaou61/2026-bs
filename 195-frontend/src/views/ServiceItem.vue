<template><DataPage title="事项档案" description="事项编号、事项名称、事项类别、责任窗口、启用时间和事项状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CENTER', 'WINDOW', 'CITIZEN']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addServiceItem, deleteServiceItem, finishServiceItem, getServiceItemPage, processServiceItem, updateServiceItem } from '../api'
const api = { page: getServiceItemPage, add: addServiceItem, update: updateServiceItem, delete: deleteServiceItem, process: processServiceItem, finish: finishServiceItem }
const columns = [{ prop: 'recordNo', label: '事项编号' }, { prop: 'recordName', label: '事项名称' }, { prop: 'category', label: '事项类别' }, { prop: 'ownerName', label: '责任窗口' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '事项状态' }, { prop: 'remark', label: '事项说明' }]
const formFields = [{ prop: 'recordNo', label: '事项编号' }, { prop: 'recordName', label: '事项名称' }, { prop: 'category', label: '事项类别' }, { prop: 'ownerName', label: '责任窗口' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '事项状态', type: 'select' }, { prop: 'remark', label: '事项说明', type: 'textarea' }]
</script>

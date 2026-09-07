<template><DataPage title="漂流图书" description="图书编号、图书名称、图书类型、当前持有人、入库时间和图书状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'LIBRARY', 'CURATOR', 'STUDENT', 'CLUB']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addBookProfile, deleteBookProfile, finishBookProfile, getBookProfilePage, processBookProfile, updateBookProfile } from '../api'
const api = { page: getBookProfilePage, add: addBookProfile, update: updateBookProfile, delete: deleteBookProfile, process: processBookProfile, finish: finishBookProfile }
const columns = [{ prop: 'recordNo', label: '图书编号' }, { prop: 'recordName', label: '图书名称' }, { prop: 'category', label: '图书类型' }, { prop: 'ownerName', label: '当前持有人' }, { prop: 'planTime', label: '入库时间' }, { prop: 'status', label: '图书状态' }, { prop: 'remark', label: '图书说明' }]
const formFields = [{ prop: 'recordNo', label: '图书编号' }, { prop: 'recordName', label: '图书名称' }, { prop: 'category', label: '图书类型' }, { prop: 'ownerName', label: '当前持有人' }, { prop: 'planTime', label: '入库时间' }, { prop: 'status', label: '图书状态', type: 'select' }, { prop: 'remark', label: '图书说明', type: 'textarea' }]
</script>

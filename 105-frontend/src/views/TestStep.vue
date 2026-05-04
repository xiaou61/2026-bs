<template>
  <DataPage title="用例步骤" description="用例步骤维护与接口测试协作管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="{ status: 'ACTIVE' }" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getTestStepPage, addTestStep, updateTestStep, deleteTestStep } from '../api'
const api = { page: getTestStepPage, add: addTestStep, update: updateTestStep, delete: deleteTestStep }
const columns = [{ prop: 'caseId', label: '用例ID' }, { prop: 'stepName', label: '步骤名称' }, { prop: 'stepOrder', label: '步骤序号' }, { prop: 'assertExpression', label: '断言表达式' }, { prop: 'status', label: '状态' }]
const formFields = [{ prop: 'caseId', label: '用例ID', type: 'number' }, { prop: 'stepName', label: '步骤名称' }, { prop: 'stepOrder', label: '步骤序号', type: 'number' }, { prop: 'requestData', label: '请求数据', type: 'textarea' }, { prop: 'assertExpression', label: '断言表达式', type: 'textarea' }, { prop: 'extractVariable', label: '提取变量' }, { prop: 'status', label: '状态', type: 'select', options: [{ label: 'ACTIVE', value: 'ACTIVE' }, { label: 'DRAFT', value: 'DRAFT' }, { label: 'PUBLISHED', value: 'PUBLISHED' }, { label: 'RUNNING', value: 'RUNNING' }, { label: 'FINISHED', value: 'FINISHED' }] }]
const rowActions = []
const handleAction = () => {}
</script>

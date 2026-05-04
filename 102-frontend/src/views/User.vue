<template>
  <DataPage ref="pageRef" title="账号权限" description="维护管理员、律师、助理和委托人账号。" :api="api" :filters="filters" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="{ role: 'CLIENT', status: 1, password: '123456' }" @row-action="handleAction" />
</template>

<script setup>
import { ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { addUser, deleteUser, getUserPage, updateUser } from '../api'

const pageRef = ref()

const statusMap = { 0: { label: '停用', type: 'info' }, 1: { label: '启用', type: 'success' }, 2: { label: '完成', type: 'primary' }, 3: { label: '关闭', type: 'danger' } }
const statusOptions = [{ label: '停用', value: 0 }, { label: '启用', value: 1 }, { label: '完成', value: 2 }, { label: '关闭', value: 3 }]
const roleMap = { ADMIN: { label: '管理员', type: 'danger' }, LAWYER: { label: '律师', type: 'success' }, ASSISTANT: { label: '助理', type: 'warning' }, CLIENT: { label: '委托人', type: 'primary' } }
const roleOptions = [{ label: '管理员', value: 'ADMIN' }, { label: '律师', value: 'LAWYER' }, { label: '助理', value: 'ASSISTANT' }, { label: '委托人', value: 'CLIENT' }]
const serviceMap = { 0: { label: '暂停', type: 'info' }, 1: { label: '接案中', type: 'success' } }
const serviceOptions = [{ label: '暂停', value: 0 }, { label: '接案中', value: 1 }]
const caseStatusMap = { 0: { label: '受理中', type: 'warning' }, 1: { label: '处理中', type: 'primary' }, 2: { label: '待归档', type: 'success' }, 3: { label: '已结案', type: 'info' } }
const caseStatusOptions = [{ label: '受理中', value: 0 }, { label: '处理中', value: 1 }, { label: '待归档', value: 2 }, { label: '已结案', value: 3 }]
const priorityMap = { 低: { label: '低', type: 'info' }, 普通: { label: '普通', type: 'primary' }, 高: { label: '高', type: 'danger' } }
const priorityOptions = [{ label: '低', value: '低' }, { label: '普通', value: '普通' }, { label: '高', value: '高' }]
const nodeMap = { 0: { label: '未开始', type: 'info' }, 1: { label: '进行中', type: 'primary' }, 2: { label: '已完成', type: 'success' } }
const nodeOptions = [{ label: '未开始', value: 0 }, { label: '进行中', value: 1 }, { label: '已完成', value: 2 }]
const riskOptions = [{ label: '低', value: '低' }, { label: '中', value: '中' }, { label: '高', value: '高' }]
const docMap = { 0: { label: '草稿', type: 'info' }, 1: { label: '已生成', type: 'primary' }, 2: { label: '已通过', type: 'success' }, 3: { label: '已驳回', type: 'danger' } }
const docOptions = [{ label: '草稿', value: 0 }, { label: '已生成', value: 1 }, { label: '已通过', value: 2 }, { label: '已驳回', value: 3 }]
const appointmentMap = { 0: { label: '待确认', type: 'warning' }, 1: { label: '已确认', type: 'primary' }, 2: { label: '已取消', type: 'danger' }, 3: { label: '已完成', type: 'success' } }
const appointmentOptions = [{ label: '待确认', value: 0 }, { label: '已确认', value: 1 }, { label: '已取消', value: 2 }, { label: '已完成', value: 3 }]
const verifyMap = { 0: { label: '待核验', type: 'warning' }, 1: { label: '通过', type: 'success' }, 2: { label: '驳回', type: 'danger' } }
const verifyOptions = [{ label: '待核验', value: 0 }, { label: '通过', value: 1 }, { label: '驳回', value: 2 }]
const payMap = { 0: { label: '未支付', type: 'warning' }, 1: { label: '已支付', type: 'success' }, 2: { label: '已退款', type: 'danger' } }
const payOptions = [{ label: '未支付', value: 0 }, { label: '已支付', value: 1 }, { label: '已退款', value: 2 }]

const api = { page: getUserPage, add: addUser, update: updateUser, delete: deleteUser }
const filters = [{ type: 'input', prop: 'keyword', label: '关键词' }, { type: 'select', prop: 'role', label: '角色', options: roleOptions }, { type: 'select', prop: 'status', label: '状态', options: statusOptions }]
const columns = [{ prop: 'username', label: '用户名' }, { prop: 'nickname', label: '昵称' }, { prop: 'role', label: '角色', map: roleMap }, { prop: 'orgName', label: '组织' }, { prop: 'phone', label: '电话' }, { prop: 'status', label: '状态', map: statusMap }]
const formFields = [{ prop: 'username', label: '用户名', required: true }, { prop: 'password', label: '密码', password: true }, { prop: 'nickname', label: '昵称', required: true }, { prop: 'role', label: '角色', type: 'select', options: roleOptions }, { prop: 'orgName', label: '组织' }, { prop: 'phone', label: '电话' }, { prop: 'email', label: '邮箱' }, { prop: 'status', label: '状态', type: 'select', options: statusOptions }]
const rowActions = []

const handleAction = async (name, row) => {
  return name && row
}
</script>

<template>
  <DataPage
    title="学生档案"
    description="维护学生编号、姓名、班级、重点标签和联系电话，支撑个案评估与持续关注"
    :api="api"
    :columns="columns"
    :form-fields="formFields"
    :row-actions="rowActions"
    :defaults="defaults"
    :can-create="canManage"
    :can-edit="canManage"
    :can-delete="canManage"
    @row-action="handleAction"
  />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getStudentProfilePage, addStudentProfile, updateStudentProfile, deleteStudentProfile, activateStudentProfile, finishStudentProfile } from '../api'

const api = { page: getStudentProfilePage, add: addStudentProfile, update: updateStudentProfile, delete: deleteStudentProfile }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'TEACHER', 'COUNSELOR'].includes(role.value))
const columns = [
  { prop: 'studentNo', label: '学生编号' },
  { prop: 'studentName', label: '学生姓名', width: 160 },
  { prop: 'className', label: '所在班级', width: 180 },
  { prop: 'focusTag', label: '重点标签', width: 140 },
  { prop: 'phoneNumber', label: '联系电话', width: 140 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'studentNo', label: '学生编号' },
  { prop: 'studentName', label: '学生姓名' },
  { prop: 'className', label: '所在班级' },
  { prop: 'focusTag', label: '重点标签' },
  { prop: 'phoneNumber', label: '联系电话' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '在册', value: 'ACTIVE' }, { label: '归档', value: 'FINISHED' }] }
]
const rowActions = computed(() => canManage.value ? [
  { command: 'activate', label: '在册', type: 'success' },
  { command: 'finish', label: '归档', type: 'warning' }
] : [])
const defaults = { status: 'ACTIVE' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateStudentProfile(row.id)
  if (command === 'finish') await finishStudentProfile(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>









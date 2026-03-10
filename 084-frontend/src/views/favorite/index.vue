<template>
  <div class="page-container">
    <el-row :gutter="16">
      <el-col :span="12">
        <el-card>
          <template #header>我的收藏</template>
          <el-table :data="favoriteRows" v-loading="loadingFav">
            <el-table-column label="标题" min-width="180">
              <template #default="{ row }">{{ row.material?.title }}</template>
            </el-table-column>
            <el-table-column label="下载" width="80">
              <template #default="{ row }">{{ row.material?.downloadCount }}</template>
            </el-table-column>
            <el-table-column label="操作" width="120">
              <template #default="{ row }">
                <el-button link type="danger" @click="cancel(row.material?.id)">取消</el-button>
                <el-button link type="primary" @click="download(row.material?.id)">下载</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>

      <el-col :span="12">
        <el-card>
          <template #header>学习清单</template>
          <el-table :data="studyRows" v-loading="loadingStudy">
            <el-table-column prop="materialId" label="资料ID" width="80" />
            <el-table-column prop="progress" label="进度(%)" width="100" />
            <el-table-column prop="note" label="备注" min-width="150" />
            <el-table-column label="操作" width="120">
              <template #default="{ row }">
                <el-button link type="primary" @click="openEdit(row)">编辑</el-button>
                <el-button link type="danger" @click="removeStudy(row.id)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
    </el-row>

    <el-dialog v-model="dialogVisible" title="编辑学习进度" width="520px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="资料ID"><el-input v-model="form.materialId" disabled /></el-form-item>
        <el-form-item label="学习进度"><el-input-number v-model="form.progress" :min="0" :max="100" /></el-form-item>
        <el-form-item label="备注"><el-input v-model="form.note" type="textarea" /></el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio :label="1">进行中</el-radio>
            <el-radio :label="2">已完成</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveStudy">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { cancelFavorite, deleteStudy, downloadMaterial, getFavoriteList, getStudyList, updateStudy } from '../../api'

const loadingFav = ref(false)
const loadingStudy = ref(false)
const favoriteRows = ref([])
const studyRows = ref([])
const dialogVisible = ref(false)
const form = reactive({ id: null, materialId: null, progress: 0, note: '', status: 1 })

const loadFavorites = async () => {
  loadingFav.value = true
  try {
    const res = await getFavoriteList()
    favoriteRows.value = res.data || []
  } finally {
    loadingFav.value = false
  }
}

const loadStudy = async () => {
  loadingStudy.value = true
  try {
    const res = await getStudyList()
    studyRows.value = res.data || []
  } finally {
    loadingStudy.value = false
  }
}

const cancel = async (materialId) => {
  if (!materialId) return
  await cancelFavorite(materialId)
  ElMessage.success('已取消收藏')
  loadFavorites()
}

const download = async (materialId) => {
  if (!materialId) return
  await downloadMaterial(materialId)
  ElMessage.success('下载记录已添加')
  loadFavorites()
}

const openEdit = (row) => {
  Object.assign(form, row)
  dialogVisible.value = true
}

const saveStudy = async () => {
  await updateStudy(form)
  ElMessage.success('保存成功')
  dialogVisible.value = false
  loadStudy()
}

const removeStudy = async (id) => {
  await deleteStudy(id)
  ElMessage.success('删除成功')
  loadStudy()
}

onMounted(async () => {
  await loadFavorites()
  await loadStudy()
})
</script>

<style scoped>
.page-container { padding: 10px; }
</style>

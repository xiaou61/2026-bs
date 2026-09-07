<template>
  <el-card>
    <template #header>
      <div class="header">
        <span>课程管理</span>
        <div>
          <el-input v-model="keyword" placeholder="搜索课程" style="width: 200px; margin-right: 10px" @keyup.enter="loadData" clearable />
          <el-button type="primary" @click="openDialog()">添加课程</el-button>
        </div>
      </div>
    </template>
    <el-table :data="courses" stripe>
      <el-table-column prop="id" label="ID" width="60" />
      <el-table-column prop="title" label="课程标题" min-width="200" />
      <el-table-column prop="learnCount" label="学习人数" width="100" />
      <el-table-column prop="chapterCount" label="章节数" width="80" />
      <el-table-column prop="score" label="评分" width="80" />
      <el-table-column prop="status" label="状态" width="80">
        <template #default="{ row }">
          <el-switch v-model="row.status" :active-value="1" :inactive-value="0" @change="updateStatus(row)" />
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200">
        <template #default="{ row }">
          <el-button link type="primary" @click="openDialog(row)">编辑</el-button>
          <el-button link type="primary" @click="openChapters(row)">章节</el-button>
          <el-button link type="danger" @click="handleDelete(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination v-model:current-page="pageNum" :page-size="10" :total="total" layout="total, prev, pager, next" @current-change="loadData" style="margin-top: 20px" />

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑课程' : '添加课程'" width="600px">
      <el-form :model="form" label-width="80px">
        <el-form-item label="标题"><el-input v-model="form.title" /></el-form-item>
        <el-form-item label="封面"><el-input v-model="form.cover" /></el-form-item>
        <el-form-item label="描述"><el-input v-model="form.description" type="textarea" rows="3" /></el-form-item>
        <el-form-item label="分类">
          <el-select v-model="form.categoryId" placeholder="选择分类">
            <el-option v-for="cat in categories" :key="cat.id" :label="cat.name" :value="cat.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="免费">
          <el-radio-group v-model="form.isFree">
            <el-radio :label="1">免费</el-radio>
            <el-radio :label="0">付费</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="chapterVisible" title="章节管理" width="800px">
      <el-button type="primary" size="small" @click="openChapterDialog()" style="margin-bottom: 15px">添加章节</el-button>
      <div v-for="chapter in chapters" :key="chapter.id" class="chapter-item">
        <div class="chapter-header">
          <strong>{{ chapter.title }}</strong>
          <div>
            <el-button link type="primary" size="small" @click="openChapterDialog(chapter)">编辑</el-button>
            <el-button link type="primary" size="small" @click="openVideoDialog(chapter)">添加视频</el-button>
            <el-button link type="danger" size="small" @click="deleteChapter(chapter)">删除</el-button>
          </div>
        </div>
        <div class="video-list">
          <div v-for="video in chapter.videos" :key="video.id" class="video-item">
            <span>{{ video.title }} ({{ video.duration }}秒)</span>
            <div>
              <el-button link size="small" @click="openVideoDialog(chapter, video)">编辑</el-button>
              <el-button link type="danger" size="small" @click="deleteVideo(video)">删除</el-button>
            </div>
          </div>
        </div>
      </div>
    </el-dialog>

    <el-dialog v-model="chapterDialogVisible" :title="chapterForm.id ? '编辑章节' : '添加章节'" width="400px">
      <el-form :model="chapterForm" label-width="60px">
        <el-form-item label="标题"><el-input v-model="chapterForm.title" /></el-form-item>
        <el-form-item label="排序"><el-input-number v-model="chapterForm.sort" :min="0" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="chapterDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitChapter">确定</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="videoDialogVisible" :title="videoForm.id ? '编辑视频' : '添加视频'" width="500px">
      <el-form :model="videoForm" label-width="80px">
        <el-form-item label="标题"><el-input v-model="videoForm.title" /></el-form-item>
        <el-form-item label="视频URL"><el-input v-model="videoForm.url" /></el-form-item>
        <el-form-item label="时长(秒)"><el-input-number v-model="videoForm.duration" :min="0" /></el-form-item>
        <el-form-item label="排序"><el-input-number v-model="videoForm.sort" :min="0" /></el-form-item>
        <el-form-item label="免费试看">
          <el-switch v-model="videoForm.isFree" :active-value="1" :inactive-value="0" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="videoDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitVideo">确定</el-button>
      </template>
    </el-dialog>
  </el-card>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import api from '../api'

const courses = ref([])
const categories = ref([])
const chapters = ref([])
const total = ref(0)
const pageNum = ref(1)
const keyword = ref('')
const dialogVisible = ref(false)
const chapterVisible = ref(false)
const chapterDialogVisible = ref(false)
const videoDialogVisible = ref(false)
const currentCourse = ref(null)
const form = reactive({ id: null, title: '', cover: '', description: '', categoryId: null, isFree: 1, status: 1 })
const chapterForm = reactive({ id: null, courseId: null, title: '', sort: 0 })
const videoForm = reactive({ id: null, chapterId: null, courseId: null, title: '', url: '', duration: 0, sort: 0, isFree: 0 })

onMounted(async () => {
  loadData()
  categories.value = await api.getCategoryList()
})

const loadData = async () => {
  const res = await api.getCourseList({ pageNum: pageNum.value, pageSize: 10, keyword: keyword.value })
  courses.value = res.records
  total.value = res.total
}

const openDialog = (row = null) => {
  if (row) {
    Object.assign(form, row)
  } else {
    Object.assign(form, { id: null, title: '', cover: '', description: '', categoryId: null, isFree: 1, status: 1 })
  }
  dialogVisible.value = true
}

const handleSubmit = async () => {
  if (form.id) {
    await api.updateCourse(form)
  } else {
    await api.addCourse(form)
  }
  ElMessage.success('保存成功')
  dialogVisible.value = false
  loadData()
}

const updateStatus = async row => {
  await api.updateCourseStatus({ id: row.id, status: row.status })
  ElMessage.success('更新成功')
}

const handleDelete = async row => {
  await ElMessageBox.confirm('确定删除该课程？', '提示')
  await api.deleteCourse(row.id)
  ElMessage.success('删除成功')
  loadData()
}

const openChapters = async row => {
  currentCourse.value = row
  chapters.value = await api.getCourseChapters(row.id)
  chapterVisible.value = true
}

const openChapterDialog = (chapter = null) => {
  if (chapter) {
    Object.assign(chapterForm, chapter)
  } else {
    Object.assign(chapterForm, { id: null, courseId: currentCourse.value.id, title: '', sort: 0 })
  }
  chapterDialogVisible.value = true
}

const submitChapter = async () => {
  if (chapterForm.id) {
    await api.updateChapter(chapterForm)
  } else {
    await api.addChapter(chapterForm)
  }
  ElMessage.success('保存成功')
  chapterDialogVisible.value = false
  chapters.value = await api.getCourseChapters(currentCourse.value.id)
}

const deleteChapter = async chapter => {
  await ElMessageBox.confirm('确定删除该章节？', '提示')
  await api.deleteChapter(chapter.id)
  ElMessage.success('删除成功')
  chapters.value = await api.getCourseChapters(currentCourse.value.id)
}

const openVideoDialog = (chapter, video = null) => {
  if (video) {
    Object.assign(videoForm, video)
  } else {
    Object.assign(videoForm, { id: null, chapterId: chapter.id, courseId: currentCourse.value.id, title: '', url: '', duration: 0, sort: 0, isFree: 0 })
  }
  videoDialogVisible.value = true
}

const submitVideo = async () => {
  if (videoForm.id) {
    await api.updateVideo(videoForm)
  } else {
    await api.addVideo(videoForm)
  }
  ElMessage.success('保存成功')
  videoDialogVisible.value = false
  chapters.value = await api.getCourseChapters(currentCourse.value.id)
}

const deleteVideo = async video => {
  await ElMessageBox.confirm('确定删除该视频？', '提示')
  await api.deleteVideo(video.id)
  ElMessage.success('删除成功')
  chapters.value = await api.getCourseChapters(currentCourse.value.id)
}
</script>

<style scoped>
.header { display: flex; justify-content: space-between; align-items: center; }
.chapter-item { border: 1px solid #eee; border-radius: 4px; margin-bottom: 10px; }
.chapter-header { display: flex; justify-content: space-between; padding: 10px 15px; background: #f5f7fa; }
.video-list { padding: 10px 15px; }
.video-item { display: flex; justify-content: space-between; padding: 8px 0; border-bottom: 1px dashed #eee; }
</style>

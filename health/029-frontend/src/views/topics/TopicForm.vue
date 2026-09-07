<template>
  <section class="page-shell">
    <div class="page-head">
      <p class="eyebrow">Community Draft</p>
      <h1>发起一个新话题</h1>
      <p>把你的调养心得、问题和创作经验发出来，和社区一起完善答案。</p>
    </div>

    <el-card class="form-card" shadow="never">
      <el-form label-position="top" :model="form">
        <el-form-item label="话题标题">
          <el-input v-model="form.title" maxlength="200" show-word-limit />
        </el-form-item>
        <el-row :gutter="20">
          <el-col :md="12" :xs="24">
            <el-form-item label="分类">
              <el-input v-model="form.category" placeholder="例如：日常调养 / 创作经验" />
            </el-form-item>
          </el-col>
          <el-col :md="12" :xs="24">
            <el-form-item label="标签">
              <el-input v-model="form.tags" placeholder="多个标签请用逗号分隔" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="内容">
          <el-input v-model="form.content" type="textarea" :rows="10" />
        </el-form-item>
        <div class="actions">
          <el-button @click="router.push({ name: 'topics' })">取消</el-button>
          <el-button type="primary" :loading="submitting" @click="submitTopic">发布话题</el-button>
        </div>
      </el-form>
    </el-card>
  </section>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { createTopic } from '../../api/topic'

const router = useRouter()
const submitting = ref(false)
const form = reactive({
  title: '',
  category: '',
  tags: '',
  content: ''
})

const submitTopic = async () => {
  if (!form.title.trim() || !form.content.trim()) {
    ElMessage.warning('请先补全标题和内容')
    return
  }

  submitting.value = true
  try {
    await createTopic({
      title: form.title.trim(),
      category: form.category.trim(),
      tags: form.tags.trim(),
      content: form.content.trim()
    })
    ElMessage.success('话题已发布')
    router.push({ name: 'my-topics' })
  } catch (error) {
    ElMessage.error(error?.message || '发布失败')
  } finally {
    submitting.value = false
  }
}
</script>

<style scoped>
.page-shell {
  max-width: 920px;
  margin: 0 auto;
}

.page-head {
  padding: 30px 32px;
  border-radius: 28px;
  background: linear-gradient(135deg, #163b2d 0%, #2f6a4b 58%, #c7813e 100%);
  color: #f6f2e8;
  margin-bottom: 20px;
}

.eyebrow {
  letter-spacing: 0.16em;
  text-transform: uppercase;
  font-size: 12px;
  opacity: 0.8;
  margin-bottom: 8px;
}

.form-card {
  border-radius: 24px;
}

.actions {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
}

@media (max-width: 768px) {
  .actions {
    flex-direction: column;
  }
}
</style>

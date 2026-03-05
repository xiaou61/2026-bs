<template>
  <view class="container">
    <view class="form-item">
      <text class="label">作品标题</text>
      <input v-model="form.title" placeholder="请输入作品标题" />
    </view>
    <view class="form-item">
      <text class="label">作品内容</text>
      <textarea v-model="form.content" placeholder="请输入作品内容" :maxlength="-1" @input="onContentChange" />
      <text class="word-count">已输入 {{ wordCount }} 字</text>
    </view>
    <view class="bottom-bar">
      <button class="btn-submit" @click="handleSubmit">{{ isEdit ? '保存修改' : '提交作品' }}</button>
    </view>
  </view>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { submitWork, updateWork, getWork } from '../../api'

const form = reactive({ competitionId: null, title: '', content: '' })
const isEdit = ref(false)
const workId = ref(null)

const wordCount = computed(() => form.content.length)
const onContentChange = (e) => { form.content = e.detail.value }

const handleSubmit = async () => {
  if (!form.title) return uni.showToast({ title: '请输入标题', icon: 'none' })
  if (!form.content) return uni.showToast({ title: '请输入内容', icon: 'none' })
  try {
    if (isEdit.value) {
      await updateWork({ id: workId.value, title: form.title, content: form.content })
    } else {
      await submitWork(form)
    }
    uni.showToast({ title: '提交成功', icon: 'success' })
    setTimeout(() => uni.navigateBack(), 1000)
  } catch (e) {}
}

onMounted(async () => {
  const pages = getCurrentPages()
  const page = pages[pages.length - 1]
  if (page.options.competitionId) {
    form.competitionId = parseInt(page.options.competitionId)
  }
  if (page.options.id) {
    isEdit.value = true
    workId.value = parseInt(page.options.id)
    const res = await getWork(workId.value)
    form.title = res.data.title
    form.content = res.data.content
    form.competitionId = res.data.competitionId
  }
})
</script>

<style scoped>
.container { padding-bottom: 150rpx; }
.form-item { background: #fff; padding: 24rpx; margin-bottom: 20rpx; }
.form-item .label { font-size: 28rpx; font-weight: bold; margin-bottom: 16rpx; display: block; }
.form-item input { height: 80rpx; background: #f5f5f5; border-radius: 12rpx; padding: 0 24rpx; font-size: 28rpx; }
.form-item textarea { width: 100%; height: 500rpx; background: #f5f5f5; border-radius: 12rpx; padding: 24rpx; font-size: 28rpx; box-sizing: border-box; }
.word-count { display: block; text-align: right; font-size: 24rpx; color: #999; margin-top: 10rpx; }
.bottom-bar { position: fixed; bottom: 0; left: 0; right: 0; padding: 20rpx 30rpx; background: #fff; box-shadow: 0 -4rpx 12rpx rgba(0,0,0,0.05); }
.btn-submit { background: #409EFF; color: #fff; border: none; height: 90rpx; line-height: 90rpx; border-radius: 12rpx; font-size: 32rpx; }
</style>

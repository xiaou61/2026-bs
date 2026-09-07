<template>
  <el-config-provider namespace="ep">
    <div class="layout">
      <header class="header">水稻收割预约系统</header>
      <main class="main">
        <RouterView />
      </main>
      <footer class="footer">© 2025 乡村振兴 · 收割预约</footer>
    </div>
  </el-config-provider>
</template>

<script setup>
</script>

<style scoped>
.layout {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  background: #f6f8fa;
}
.header {
  background: #409eff;
  color: #fff;
  padding: 16px 24px;
  font-size: 18px;
}
.main {
  flex: 1;
  padding: 24px;
}
.footer {
  text-align: center;
  padding: 12px;
  color: #888;
  font-size: 13px;
}
</style>

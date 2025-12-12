<template>
  <div class="footer-content">
    <div class="footer-section">
      <h4>关于我们</h4>
      <p>中药食疗推荐交流平台是一个专注于健康食疗的社区。</p>
    </div>
    
    <div class="footer-section">
      <h4>快速链接</h4>
      <ul>
        <li><router-link to="/">首页</router-link></li>
        <li><router-link to="/recipes">食谱库</router-link></li>
        <li><router-link to="/ingredients">食材库</router-link></li>
        <li><router-link to="/topics">社区讨论</router-link></li>
      </ul>
    </div>
    
    <div class="footer-section">
      <h4>联系方式</h4>
      <p>Email: info@herbal-food.com</p>
      <p>Phone: +86 XXX-XXXX-XXXX</p>
    </div>
    
    <div class="footer-section">
      <h4>关注我们</h4>
      <div class="social-links">
        <a href="#" title="微博">微博</a>
        <a href="#" title="微信">微信</a>
        <a href="#" title="抖音">抖音</a>
      </div>
    </div>
    
    <div style="text-align: center; margin-top: 20px; border-top: 1px solid #ddd; padding-top: 20px;">
      <p>&copy; 2025 中药食疗推荐交流平台 All Rights Reserved</p>
    </div>
  </div>
</template>

<script setup>
</script>

<style scoped>
.footer-content {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 40px;
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
}

.footer-section h4 {
  margin: 0 0 15px 0;
  color: #27ae60;
  font-size: 14px;
  font-weight: 600;
}

.footer-section p {
  margin: 0 0 8px 0;
  color: #666;
  font-size: 12px;
  line-height: 1.6;
}

.footer-section ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.footer-section li {
  margin-bottom: 8px;
}

.footer-section a {
  color: #666;
  font-size: 12px;
  text-decoration: none;
  transition: color 0.3s;
}

.footer-section a:hover {
  color: #27ae60;
}

.social-links {
  display: flex;
  gap: 15px;
}

@media (max-width: 768px) {
  .footer-content {
    grid-template-columns: repeat(2, 1fr);
    gap: 20px;
  }
}

@media (max-width: 480px) {
  .footer-content {
    grid-template-columns: 1fr;
  }
}
</style>

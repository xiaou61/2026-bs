import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  plugins: [vue()],
  server: {
    port: 3152,
    proxy: {
      '/api': {
        target: 'http://localhost:8052',
        changeOrigin: true
      }
    }
  }
})

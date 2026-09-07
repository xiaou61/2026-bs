import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  plugins: [vue()],
  server: {
    port: 3072,
    proxy: {
      '/api': {
        target: process.env.VITE_API_TARGET || 'http://localhost:8072',
        changeOrigin: true
      }
    }
  }
})

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  plugins: [vue()],
  server: {
    port: 3077,
    proxy: {
      '/api': {
        target: process.env.VITE_API_TARGET || 'http://localhost:8077',
        changeOrigin: true
      }
    }
  }
})

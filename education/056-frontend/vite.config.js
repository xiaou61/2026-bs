import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  plugins: [vue()],
  server: {
    port: 3056,
    proxy: {
      '/api': {
        target: 'http://localhost:8056',
        changeOrigin: true
      },
      '/uploads': {
        target: 'http://localhost:8056',
        changeOrigin: true
      }
    }
  }
})

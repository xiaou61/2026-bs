import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { resolve } from 'path'

export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': resolve(__dirname, 'src')
    }
  },
  server: {
    port: 3044,
    proxy: {
      '/api': {
        target: 'http://localhost:8044',
        changeOrigin: true
      }
    }
  }
})

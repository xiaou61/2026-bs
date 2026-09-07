import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { fileURLToPath, URL } from 'node:url'

export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  server: {
    port: 5173,
    proxy: {
      '/api': {
        target: 'http://localhost:8011',
        changeOrigin: true
      },
      '/videos': {
        target: 'http://localhost:8011',
        changeOrigin: true
      },
      '/covers': {
        target: 'http://localhost:8011',
        changeOrigin: true
      },
      '/avatars': {
        target: 'http://localhost:8011',
        changeOrigin: true
      }
    }
  }
})


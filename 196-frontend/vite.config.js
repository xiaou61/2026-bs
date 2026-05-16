import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
export default defineConfig({
  plugins: [vue()],
  server: { port: 3196, proxy: { '/api': { target: 'http://localhost:8196', changeOrigin: true } } }
})

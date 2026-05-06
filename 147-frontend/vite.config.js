import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
export default defineConfig({
  plugins: [vue()],
  server: { port: 3147, proxy: { '/api': { target: 'http://localhost:8147', changeOrigin: true } } }
})








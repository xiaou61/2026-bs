import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
export default defineConfig({
  plugins: [vue()],
  server: { port: 3119, proxy: { '/api': { target: 'http://localhost:8119', changeOrigin: true } } }
})

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
export default defineConfig({
  root: process.cwd(),
  plugins: [vue()],
  server: { port: 3179, proxy: { '/api': { target: 'http://localhost:8179', changeOrigin: true } } }
})

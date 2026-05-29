#!/usr/bin/env python3
"""Comprehensive coverage audit"""
import os, re, sys
sys.stdout.reconfigure(encoding='utf-8')

root = os.getcwd()
dir = os.path.join(root, 'docs-site', 'projects')
files = sorted([f for f in os.listdir(dir) if f.endswith('.md')])
total = len(files)
hasR = hasA = hasD = hasS = hasN = hasT = hasDB = hasFP = hasAPI = 0
noA = []
noR = []

for f in files:
    c = open(os.path.join(dir, f), encoding='utf-8').read()
    num = f.replace('.md','')

    # Roles
    if '## 角色权限' in c:
        hasR += 1
    else:
        noR.append(num)

    # Accounts - check for any real data (table with backtick usernames/passwords)
    am = re.search(r'### 默认账号\s*\n(.*?)(?=\n## |\n### |$)', c, re.DOTALL)
    if am:
        sec = am.group(1)
        # Table rows with backtick content (single or double backticks)
        if (re.search(r'\|\s*`+[^`]+`+\s*\|', sec) or
            re.search(r'`(\w+)/(\w+)`', sec)) and '请查看项目 README' not in sec:
            hasA += 1
        else:
            noA.append(num)
    else:
        noA.append(num)

    # Project description
    dm = re.search(r'## 项目简介\s*\n\n(.*?)(?=\n## )', c, re.DOTALL)
    if dm and len(dm.group(1).strip()) > 10:
        hasD += 1

    if '## 项目结构' in c:
        hasS += 1
    if '### 开发注意事项' in c:
        hasN += 1
    if '特色亮点' in c or '技术亮点' in c:
        hasT += 1
    if '## 数据库设计' in c:
        hasDB += 1
    if '## 前端页面' in c:
        hasFP += 1
    if '## API 接口' in c:
        hasAPI += 1

p = lambda n: f'{n/total*100:.1f}%'
print(f'总项目: {total}')
print(f'角色权限: {hasR} ({p(hasR)})')
print(f'真实账号: {hasA} ({p(hasA)})')
print(f'项目简介: {hasD} ({p(hasD)})')
print(f'项目结构: {hasS} ({p(hasS)})')
print(f'开发注意事项: {hasN} ({p(hasN)})')
print(f'技术亮点: {hasT} ({p(hasT)})')
print(f'数据库: {hasDB} ({p(hasDB)})')
print(f'前端页面: {hasFP} ({p(hasFP)})')
print(f'API接口: {hasAPI} ({p(hasAPI)})')

print(f'\n缺少账号({len(noA)}个): {", ".join(noA[:30])}...')
print(f'\n缺少角色({len(noR)}个): {", ".join(noR[:30])}...')
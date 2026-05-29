#!/usr/bin/env python3
"""Find ACCOUNTS.md extraction failures"""
import os, re, sys
sys.stdout.reconfigure(encoding='utf-8')
root = os.getcwd()

for num in range(1, 200):
    n = f'{num:03d}'
    acc_path = os.path.join(root, f'{n}-backend', 'ACCOUNTS.md')
    page_path = os.path.join(root, 'docs-site', 'projects', f'{n}.md')
    if not os.path.exists(acc_path) or not os.path.exists(page_path):
        continue
    page_c = open(page_path, encoding='utf-8').read()
    # Check if page has real accounts
    am = re.search(r'### 默认账号(.*?)(?=\n## |\Z)', page_c, re.DOTALL)
    has_real = False
    if am:
        sec = am.group(1)
        has_real = bool(re.search(r'`[^`]+`', sec) and '请查看项目 README' not in sec)
    if not has_real:
        acc_c = open(acc_path, encoding='utf-8').read()
        headers = re.findall(r'^(?:##|###)\s+(.+)$', acc_c, re.M)[:5]
        has_username = '用户名' in acc_c
        has_table = bool(re.search(r'\|.+\|.+\|', acc_c))
        print(f'{n}: ACCOUNTS.md NOT extracted. username={has_username}, table={has_table}, headers={headers}')
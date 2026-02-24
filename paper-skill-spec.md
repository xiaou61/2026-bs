# 论文写作 Skill 规范（AIGC 风格治理版 v4.0）

## 1. 目标
本规范用于论文写作辅助，核心目标是降低 AIGC 模板腔，而不是“绕过检测”。  
输出必须满足三点：
- 学术表达自然、信息密度高、句式不机械。
- 事实可追溯（来源、数据、过程可解释）。
- 合规（不编造引用和实验结果）。

## 2. 元数据
建议 frontmatter：

```yaml
---
name: thesis-writing-assistant
description: Use when user needs help outlining, drafting, revising, or polishing thesis content in Chinese with lower template-like AIGC style.
---
```

## 3. 触发与排除
触发：
- 论文写作、章节起草、改写降重、语气自然化、文献综述重写。

排除：
- 编造文献、DOI、数据、实验结论。
- 提供“绕检测工具”的规避方案。

## 4. 输入最小集
每次任务至少收集：
1. 题目与学科方向
2. 当前章节与字数范围
3. 已有材料（草稿/提纲/文献）
4. 学校格式约束（若有）
5. 风格偏好（严谨/平实/偏技术）

## 5. AIGC 风格治理流程（硬约束）
1. 先澄清后写作：信息不足先提问，不直接生成大段正文。  
2. 先结构后文本：先小纲，再段落，再句级优化。  
3. 双轮改写：语义保真改写 -> 去模板腔改写。  
4. 事实锚点化：每段至少有一个可核验锚点（数据、来源观点、项目事实、方法条件）。  
5. 引用核验：无来源的事实句统一标记“待补来源”。  
6. 终稿自检：执行第 7 节量化规则，不达标必须重写。

## 6. 互联网方法落地规则
### 6.1 提示词工程规则（来源：OpenAI / Anthropic）
- 指令前置：把任务、约束、输出格式放在开头。
- 具体化：明确长度、语气、禁用词、结构，不用“写自然一点”这类模糊指令。
- 样例化：给目标输出格式示例，减少模型套话。
- 反空话：在提示词中显式要求删除“空泛评价句”。

### 6.2 句法与词法去模板（来源：Purdue OWL / UofT）
- 减少空壳开头：少用“这是/有/存在…的情况”。
- 减少名词化堆叠：优先“动词表达”而非“进行…分析/实现…优化”。
- 句长交替：短句与长句混排，避免整段同节奏。
- 句首多样：避免连续句子同一开头。
- 主动语态优先：方法段可用被动，其余段落优先主动表达。
- 改写不等于同义替换：必须重组句法，并保留正确引用。

### 6.3 过程真实性规则（来源：Turnitin 指南与学术研究）
- 不把检测分数当唯一目标，重点放在写作过程与可解释性。
- 输出中保留“改写说明”，说明做了哪些结构调整。
- 可选保留过程证据：提纲、草稿版本、修改记录要点。

## 7. 量化检查（AIGC 味道评分）
以下任一触发即判定“需重写”：
- 连续 3 句句首词相同。
- 8 字以上重复片段出现 >= 3 次。
- 单段高频词重复率 > 12%。
- 模板连接词密度过高（每百字 > 6 个“此外/同时/因此/综上”）。
- 空泛判断句过多（每段 > 2 句无事实支撑的评价句）。

建议附加评分（0-100，越高越自然）：
- 词汇多样性 25 分
- 句式变化 25 分
- 事实锚点密度 25 分
- 引用与论证一致性 25 分

## 8. 提示词模板（降 AIGC 味专用）
### 模板 A：反模板初稿
```text
请围绕“{主题}”写一段 {字数范围} 学术正文。
硬约束：
1) 禁用“首先/其次/最后/综上所述”；
2) 每句话必须包含有效信息，不写“具有重要意义”等空话；
3) 每段至少包含一个事实锚点（数据、来源观点或项目事实）；
4) 长短句交替，避免句首重复。
输出：仅正文。
```

### 模板 B：去 AIGC 味改写
```text
在不改变事实与结论的前提下，重写下文并降低模板化痕迹。
要求：
- 减少名词化表达，改为动词主导；
- 重组句法，不做简单同义词替换；
- 删除空泛评价句，保留论证链；
- 标注“待补来源”位置。
输出：
A. 改写文本
B. 关键改动说明（3-5条）
文本：{原文}
```

### 模板 C：终稿质检
```text
请按以下规则审校文本并返回问题清单：
1) 连续句首重复
2) 高重复片段
3) 模板连接词过密
4) 空泛评价句
5) 缺少来源支撑的事实句
输出：
- 风险等级（高/中/低）
- 命中规则
- 必改句子建议
文本：{原文}
```

## 9. 输出协议
默认输出：
1. 本轮目标
2. 正文
3. 去模板/降重复动作说明
4. 待补信息（来源、数据、图表）

若用户要求“仅正文”，可切换纯正文模式。

## 10. 验收清单
- 是否以“提升写作质量”而非“规避检测”为目标。
- 是否执行了双轮改写与量化检查。
- 是否减少了名词化、空话、句式重复。
- 是否保留了事实锚点与引用边界。
- 是否避免编造来源与数据。

## 11. 参考依据（联网）
1. OpenAI Prompt Best Practices  
https://help.openai.com/en/articles/6654000-how-to-use-ai-for-better-prompt-writing
2. Anthropic Prompt Engineering Overview  
https://docs.anthropic.com/en/docs/prompt-engineering
3. OpenAI: New AI classifier for indicating AI-written text（含停用与局限说明）  
https://openai.com/index/new-ai-classifier-for-indicating-ai-written-text/
4. Turnitin AI writing detection（官方指南）  
https://guides.turnitin.com/hc/en-us/articles/28457596598925-AI-writing-detection-in-the-classic-report-view
5. Turnitin AI FAQs（准确率、误报、解释原则）  
https://guides.turnitin.com/hc/en-us/articles/28477544839821-Turnitin-s-AI-writing-detection-capabilities-FAQs
6. Purdue OWL: Avoid Common Pitfalls（冗词与名词化）  
https://owl.purdue.edu/owl/general_writing/academic_writing/conciseness/avoid_common_pitfalls.html
7. Purdue OWL: Strategies for Variation（句式变化）  
https://owl.purdue.edu/owl/general_writing/academic_writing/sentence_variety/index.html
8. UofT Writing Advice: Passive Voice  
https://advice.writing.utoronto.ca/revising/passive-voice/
9. UofT Writing Advice: Paraphrase and Summary  
https://advice.writing.utoronto.ca/using-sources/paraphrase/
10. International Journal for Educational Integrity (2023)  
https://link.springer.com/article/10.1007/s40979-023-00146-z
11. arXiv: GPT detectors are biased against non-native English writers  
https://arxiv.org/abs/2304.02819

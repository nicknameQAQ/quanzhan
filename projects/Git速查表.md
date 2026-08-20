# Git 速查表（新手版）

## 一句话心智模型

**本地三步曲：改代码 → `git add`（放行李箱）→ `git commit`（封箱存档）→ `git push`（寄出去）**

## 核心命令

| 命令 | 作用 | 频率 |
|---|---|---|
| `git status` | 看当前状态（改了什么、存了什么） | 每天 100 次 |
| `git add <文件>` / `git add .` | 把改动放进暂存区 | 提交前 |
| `git commit -m "说明"` | 本地存档（**只在你电脑上**） | 每个功能一次 |
| `git push origin main` | 上传存档到 GitHub | 提交后 |
| `git pull origin main` | 下载远程新存档并合并 | 开工前/收工前 |
| `git log --oneline` | 看存档历史 | 需要时 |
| `git log --oneline --graph --all` | 看提交图（分支关系） | 需要时 |
| `git branch` | 看本地分支（* 是当前分支） | 需要时 |
| `git switch -c <分支名>` | 创建并切换分支 | 开新功能时 |
| `git switch main` | 切回主分支 | 合并前 |
| `git merge <分支名>` | 把分支合并回当前分支 | 功能完成时 |
| `git branch -d <分支名>` | 删除已合并的分支 | 合并后 |

## 提交信息规范（重要）

一句话说清"做了什么"，格式参考：`类型: 描述`

- `docs: 添加项目说明 README`（文档）
- `feat: 新增登录功能`（新功能）
- `fix: 修复空指针异常`（修 bug）
- `refactor: 重构用户类`（重构）

**别写 `1`、`2`、`asd` 这种**——三个月后你自己都看不懂。

## 新手必踩的坑

1. **`git status` 说"最新"≠真的最新**：它对比的是本地缓存的远程状态。真正确认要 `git pull`。
2. **commit 只存在本地**：没 push 之前，GitHub 上什么都没有，换电脑/删文件夹就没了。
3. **push 前先 pull**：别人改了同一文件，直接 push 会被拒。先 `git pull` 合并，再 `git push`。
4. **分支是便宜的**：开新功能就 `git switch -c xxx`，别在 main 上直接改。
5. **`.iml`、`.idea/` 不该提交**：这些是 IDEA 的本地配置文件，用 `.gitignore` 忽略。

## 每次开工/收工的固定流程

```
开工： git pull origin main        # 先同步最新代码
       git switch -c feat/xxx      # 开分支干活
改代码
       git status                  # 检查改了什么
       git add .                   # 全部暂存
       git commit -m "feat: 干完了xxx"
       git switch main             # 回主线
       git pull origin main        # 再同步一次
       git merge feat/xxx          # 合并
       git branch -d feat/xxx      # 删分支
       git push origin main        # 上传
```

## 忘了命令？查这个文件就行

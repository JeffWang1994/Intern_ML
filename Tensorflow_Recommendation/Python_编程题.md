# 编程题
## 1. 输出字典内容
    def print_directory_contents(sPath):
        import os                                       
        for sChild in os.listdir(sPath):                
            sChildPath = os.path.join(sPath,sChild)
            if os.path.isdir(sChildPath):
                print_directory_contents(sChildPath)
            else:
                print(sChildPath)

## 2. Python有多线程吗？
Python实际上不允许多线程。它有一个threading包但是如果你想加快代码运行速度，或者想并行运行，这不是一个好主意。
Python有一个机制叫全局解释器锁（GIL）。GIL保证每次只有一个线程在解释器中跑。

## 3. @classmethod, @staticmethod, @property都是什么意思?

## 4. 删除一个list中重复的元素？
    a=[1,2,4,2,4,5,6,5,7,8,9,0]
    b={}
    b=b.fromkeys(a)
    c=list(b.keys())
    c

## 5. 用sort进行排序，然后从最后一个元素开始判断
    a=[1,2,4,2,4,5,7,10,5,5,7,8,9,0,3]
    
    a.sort()
    last=a[-1]
    for i in range(len(a)-2,-1,-1):
    if last==a[i]:
    del a[i]
    else:last=a[i]
    print(a)



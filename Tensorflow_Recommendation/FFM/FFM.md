# FFM模型理论
## FFM理论背景
FFM是在FM基础上发展而来的算法，(Field-aware Factorization Machine)<br>
FFM模型引入类别的概念。例如：

|Clicked?|Country|Day|Ad_type|
|--------|-------|---|-------|
|1|USA|26/11/15|Movie|
|0|China|1/7/14|Game|
|1|China|19/2/15|Game|

上表中，Day，Country，都为同一个Field。在FFM中，每一维特征$x_i$，针对其他特征的每一种field $f_j$，都会学习一个隐向量$(v_i, f_j)$。因此隐向量不仅与特征相关，也与field相关。

假设样本的n个特征属于f个field，那么FFM的二次项就有nf个隐向量。而在FM模型中，每一维特征向量只有一个。因此FM可以看作FFM的特例，是把所有特征归属为一个field的FFM模型。那么一般的FFM模型方程为：
$$y=w_0+\sum_{i=1}^nw_ix_i+\sum_{i=1}^{n-1}\sum_{j=i+1}^n \langle{v_{i,f_j}, v_{j,f_i}}\rangle x_ix_j$$

可以看出，如果隐向量的长度为k，那么FFM的二次参数有nfk个，远多于FM的nk个。此外，由于隐向量与field相关，FFM二次项并不能够化简，其预测复杂度是$O(kn^2)$

## FFM实现
### 定义损失函数
FFM将问题定义为分类问题，因此使用logistic loss，同时加入正则化。
$$\min_{w}\sum_{i=1}^L\log{(1+exp{-y_i\phi(w, x_i)})+\frac{\lambda}{2}||w||^2}$$

### 随机梯度下降
训练FFM使用的是随机梯度下降，即每次只选一条数据进行训练。

## 参考资料
https://www.cnblogs.com/ljygoodgoodstudydaydayup/p/6340129.html


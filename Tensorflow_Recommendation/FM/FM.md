# FM模型理论
## FM背景
在计算广告和推荐系统中，CTR预估(click-through rate)是非常重要的一个环节。推荐原理主要是根据CTR预估得到的点击率来进行推荐。<br>
对于特征组合来说，通用的做法主要是：FM和Tree

## FM解决了什么问题
FM(Factorization Machine，因子分解机)主要是为了解决数据稀疏的情况下，特征怎么样组合的问题。<br>
假设数据集如下图所示:

|Clicked?|Country|Day|Ad_type|
|--------|-------|---|-------|
|1|USA|26/11/15|Movie|
|0|China|1/7/14|Game|
|1|China|19/2/15|Game|

经过one-hot编码，不可避免的样本数据会很稀疏。<br>
第二个问题是，特征空间会特别大。<br>

## 特征组合
如果不进行特征组合，那么线性模型为：
$$y=w_0+\sum_{i=1}^nw_ix_i$$
那么上式完全没有考虑特征间的关联。为了表征特征见的相关性，采用多项式模型。特征组合使用$x_ix_j$表示。那么FM模型就变成：
$$y=w_0+\sum_{i=1}^nw_ix_i+\sum_{i=1}^{n-1}\sum_{j=i+1}^nw_{ij}x_ix_j$$

## 如何求解FM模型
在FM模型中，组合特征的相关参数有$n(n-1)/2$个。由于数据稀疏，满足$x_i,x_j$都不为0的情况很少，这样会导致$w_ij$训练不出来。所以对每一个特征分量$x_i$引入辅助向量$V_i=(v_{i1},x_{i2},...,x_{ik})^T$。然后$w_ij$就为：
$$\widehat{W}=VV^T$$
因此组合特征部分就为：
$$\sum_{i=1}^{n-1}\sum_{j=i+1}^nw_{ij}x_ix_j$$
$$=\sum_{i=1}^{n-1}\sum_{j=i+1}^n\langle{v_i, v_j}\rangle x_ix_j$$
$$=1/2\sum_{f=1}^k((\sum_{i=1}^nv_{i,f}x_i)^2-\sum_{i=1}^nv_{i,f}^2x_i^2)$$
然后就可以使用随机梯度下降来进行求解：
$$\frac{\partial}{\partial w_0}y(x)=1$$
$$\frac{\partial}{\partial x_i}y(x)=x_i$$
$$\frac{\partial}{\partial v_{i,f}}y(x)=x_i\sum_{j=1}^nv_{j,f}x_j-v_{i,f}x_i^2$$


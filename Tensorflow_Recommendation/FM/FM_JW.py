"""
FM(Factorization Machine)因子分解机。
Author: Jeff Wang
Date: 2019.2.27 11:20
"""

# 导入数据集
import pandas as pd
print('Loading data...')
cols = ['user', 'item', 'rating', 'timestamp']
train = pd.read_csv('data/ua.base', delimiter='\t', names=cols)
test = pd.read_csv('data/ua.test', delimiter='\t', names=cols)
train_dic = {'user': train['user'].values, 'item': train['item'].values}
test_dic = {'user': test['user'].values, 'item': test['item'].values}
print('All dataset loaded.')
# print(train_dic)
# print(train)


# 数据预处理
import numpy as np
from scipy.sparse import csr
def dataProcess(dic, ix=None, p=None, n=0, g=0):
    """
    dic -- dictionary of feature lists. Keys are the name of features. 'user':train['user'].values
    ix -- index generator (default None)
    p -- dimension of feature space (the number of columns in the sparse matrix) (default None)
    """
    if ix is None:
        ix = dict()

    nz = n*g

    col_ix = np.empty(nz, dtype=int)

    i = 0
    for k, lis in dic.items():
        for t in range(len(lis)):
            ix[str(lis[t]) + str(k)] = ix.get(str(lis[t])+str(k), 0) + 1
            col_ix[i+t*g] = ix[str(lis[t]) + str(k)]
        i += 1

    row_ix = np.repeat(np.arange(0, n), g)
    data = np.ones(nz)
    if p is None:
        p = len(ix)

    ixx = np.where(col_ix < p)
    return csr.csr_matrix((data[ixx], (row_ix[ixx], col_ix[ixx])), shape=(n, p)), ix


print('Data preprocessing...')
x_train, ix = dataProcess({'users': train['user'].values, 'items': train['item'].values}, n=len(train.index), g=2)
x_test, ix = dataProcess({'users': test['user'].values, 'items': test['item'].values}, n=len(test.index), g=2)
x_train = x_train.todense()
x_test = x_test.todense()
y_train = train['rating'].values
y_test = test['rating'].values
print(x_train)
print('Train dataset and Test dataset is ready.')

# 估计值计算
# 我们模型的估计值由两部分构成，原始的可以理解为线性回归的部分，以及交叉特征的部分。
# 估计值计算公式： y = w_0 + \sum_{n}{w_i*x_i} + \sum_{n-1}{\sum_{j=i+1}^n{w_ij*x_i*x_j}}
import tensorflow as tf
n, p = x_train.shape

k = 10

x = tf.placeholder('float', [None, p])
y = tf.placeholder('float', [None, 1])

w0 = tf.Variable(tf.zeros([1]))
w = tf.Variable(tf.zeros([p]))

v = tf.Variable(tf.random_normal([k, p], mean=0, stddev=0.01))

linear_terms = tf.add(w0, tf.reduce_sum(tf.multiply(w, x), 1, keepdims=True))
pair_interations = 0.5 * tf.reduce_sum(
    tf.subtract(
        tf.pow(
            tf.matmul(x, tf.transpose(v)), 2),
        tf.matmul(tf.pow(x, 2), tf.transpose(tf.pow(v, 2)))
    ), axis=1, keepdims=True)

y_hat = tf.add(linear_terms, pair_interations)

# 定义损失函数
# 均方误差+L2正则化，并使用梯度下降法来调参
lambda_w = tf.constant(0.001, name='lambda_w')
lambda_v = tf.constant(0.001, name='lambda_v')

error = tf.reduce_mean(tf.square(y-y_hat))

l2_norm = tf.reduce_sum(
    tf.add(
        tf.multiply(lambda_w, tf.pow(w, 2)),
        tf.multiply(lambda_v, tf.pow(v, 2))
    ))

loss = tf.add(error, l2_norm)

train_op = tf.train.GradientDescentOptimizer(learning_rate=0.01).minimize(loss)

# 模型训练
from tqdm import tqdm_notebook as tqdm

epochs = 10
batch_size = 1000

init = tf.global_variables_initializer()
with tf.Session() as sess:
    sess.run(init)

    for i in range(100):
        _, loss_value = sess.run((train_op, loss), feed_dict={x: x_train.reshape(-1, p), y: y_train.reshape(-1, 1)})
        print(loss_value)






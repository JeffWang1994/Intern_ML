from itertools import count
from collections import defaultdict
from scipy.sparse import csr
from sklearn.feature_extraction import DictVectorizer
import numpy as np
import pandas as pd
import tensorflow as tf
from tqdm import tqdm_notebook as tqdm


# 数据预处理
# 要使用FM模型，首先要将数据处理成一个矩阵，矩阵的大小为(用户数*物品数)
# 预处理方法是使用scipy.sparse中的csr.csr_matrix函数，将独热编码生成的系数矩阵变成压缩系数行Compressed Sparse Row
# 函数形式：csr_matrix(data, indices, indptr) 其中参数分别为数值，列号，每行起始的偏移量。
def vectorize_dic(dic, ix=None, p=None, n=0, g=0):
    """
    dic -- dictionary of feature lists. Keys are the name of features
    ix -- index generator (default None)
    p -- dimension of feature space (number of columns in the sparse matrix) (default None)
    """
    if ix is None:
        ix = dict()

    nz = n * g

    col_ix = np.empty(nz, dtype=int)

    i = 0
    for k, lis in dic.items():
        for t in range(len(lis)):
            ix[str(lis[t]) + str(k)] = ix.get(str(lis[t]) + str(k), 0) + 1
            col_ix[i+t*g] = ix[str(lis[t]) + str(k)]
        i += 1

    row_ix = np.repeat(np.arange(0, n), g)
    data = np.ones(nz)
    if p is None:
        p = len(ix)

    ixx = np.where(col_ix < p)
    return csr.csr_matrix((data[ixx], (row_ix[ixx], col_ix[ixx])), shape=(n, p)), ix


# 导入数据集
cols = ['user', 'item', 'rating', 'timestamp']
train = pd.read_csv('data/ua.base', delimiter='\t', names=cols)
test = pd.read_csv('data/ua.test', delimiter='\t', names=cols)

x_train, ix = vectorize_dic({'users': train['user'].values, 'items': train['item'].values}, n=len(train.index), g=2)
x_test, ix = vectorize_dic({'users': test['user'].values, 'items': test['item'].values}, n=len(test.index), g=2)

print(x_train)
y_train = train['rating'].values
y_test = test['rating'].values
x_train = x_train.todense()
x_test = x_test.todense()

print(x_train)
print(x_train.shape)
print(x_test.shape)

# 估计值计算
# 我们模型的估计值由两部分构成，原始的可以理解为线性回归的部分，以及交叉特征的部分。
# 估计值计算公式： y = w_0 + \sum_{n}{w_i*x_i} + \sum_{n-1}{\sum_{j=i+1}^n{w_ij*x_i*x_j}}
n, p = x_train.shape

k = 10
x = tf.placeholder('float', [None, p])
y = tf.placeholder('float', [None, 1])

w0 = tf.Variable(tf.zeros([1]))
w = tf.Variable(tf.zeros([p]))

v = tf.Variable(tf.random_normal([k, p], mean=0, stddev=0.01))

linear_terms = tf.add(w0, tf.reduce_sum(tf.multiply(w, x), 1, keepdims=True))
pair_interactions = 0.5 * tf.reduce_sum(
    tf.subtract(tf.pow(tf.matmul(x, tf.transpose(v)), 2),
                tf.matmul(tf.pow(x, 2), tf.transpose(tf.pow(v, 2)))), axis=1, keepdims=True)

y_hat = tf.add(linear_terms, pair_interactions)

# 定义损失函数
# 损失函数为平方损失，还加上L2正则化，并使用梯度下降来进行参数更新。
lambda_w = tf.constant(0.001, name='lambda_w')
lambda_v = tf.constant(0.001, name='lambda_v')

l2_norm = tf.reduce_sum(tf.add(
                            tf.multiply(lambda_w, tf.pow(w, 2)),
                            tf.multiply(lambda_v, tf.pow(v, 2))))

error = tf.reduce_mean(tf.square(y-y_hat))
loss = tf.add(error, l2_norm)

train_op = tf.train.GradientDescentOptimizer(learning_rate=0.01).minimize(loss)

# 模型训练
epochs = 10

# Launch the graph
init = tf.global_variables_initializer()
with tf.Session() as sess:
    sess.run(init)

    for epoch in tqdm(range(epochs), unit='epoch'):
        perm = np.random.permutation(x_train.shape[0])
        # iterate over batches
        for bX, bY in batcher(x_train[perm], y_train[perm]):
            _, t = sess.run([train_op, loss], feed_dict={x: bX.reshape(-1, p), y: bY.reshape(-1, 1)})
            print(t)

    errors = []
    for bX, bY in batcher(x_test, y_test):
        errors.append(sess.run(error, feed_dict={x: bX.reshape(-1, p), y: bY.reshape(-1, 1)}))
        print(errors)
    RMSE = np.sqrt(np.array(errors).mean())
    print(RMSE)




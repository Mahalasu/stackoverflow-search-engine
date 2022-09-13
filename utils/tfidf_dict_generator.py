import mysql.connector
from nltk.tokenize import RegexpTokenizer
import math

f = open('YOUR STOP_WORD LIST', 'r')
stopwords_set = set()

temp = f.readline()
while temp:
    if temp[-1] == '\n':
        stopwords_set.add(temp[:-1])
    else:
        stopwords_set.add(temp)
    temp = f.readline()
f.close()



conn = mysql.connector.connect(
    host = 'localhost',
    user = 'root',
    password = '123456Qq!',
    database = 'search_engine'
)

cur = conn.cursor()
cur.execute('select * from data')
captions = list(map(lambda x : x[2], cur.fetchall()))
regexp_tokenizer = RegexpTokenizer(r'C#|c#|\w+')
idf_map = {}

for caption in captions:
    words = regexp_tokenizer.tokenize(caption)
    for word in words:
        if word in stopwords_set:
            continue
        else:
            if word in idf_map:
                idf_map[word] += 1
            else:
                idf_map[word] = 1

conn = mysql.connector.connect(
    host = 'localhost',
    user = 'root',
    password = '123456Qq!',
    database = 'search_engine'
)
cur = conn.cursor()
cur.execute('select count(*) from data')
number = cur.fetchone()[0]

for k, v in idf_map.items():
    idf_map[k] = math.log(number / (idf_map[k] + 1))

with open('./idf_dict.txt', 'w') as f:
    for k, v in idf_map.items():
        f.write(k + ' ' + str(v) + '\n')
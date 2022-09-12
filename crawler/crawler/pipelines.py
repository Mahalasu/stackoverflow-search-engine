# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: https://docs.scrapy.org/en/latest/topics/item-pipeline.html


# useful for handling different item types with a single interface
import mysql.connector

class CrawlerPipeline(object):

    def __init__(self):
        self.conn = mysql.connector.connect(
            host = 'localhost',
            user = 'root',
            password = '123456Qq!',
            database = 'search_engine'
        )

        self.cur = self.conn.cursor()

    def process_item(self, item, spider):
        self.cur.execute('''insert into data (id, url, caption, views, votes) values (%s, %s, %s, %s, %s)''', (
            int(item['_id']),
            item['link'],
            item['question'],
            item['views'],
            item['votes']
        ))
        self.conn.commit()
    
    def close_spider(self, spider):
        self.cur.close()
        self.conn.close()

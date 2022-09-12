# Define here the models for your scraped items
#
# See documentation in:
# https://docs.scrapy.org/en/latest/topics/items.html

import scrapy


class CrawlerItem(scrapy.Item):
    # define the fields for your item here like:
    _id = scrapy.Field()
    question = scrapy.Field()
    link = scrapy.Field()
    votes = scrapy.Field()
    views = scrapy.Field()

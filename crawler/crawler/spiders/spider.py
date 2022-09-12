import scrapy
from crawler.items import CrawlerItem

class SOSpider(scrapy.Spider):
    name = "stackoverflow"
    allowed_domains = ['stackoverflow.com']

    def start_requests(self):
        _url = 'https://stackoverflow.com/questions?tab=Votes&page={page}&pagesize=50'
        urls = [_url.format(page=page) for page in range(2001, 2002)]
        for url in urls:
            yield scrapy.Request(url=url, callback=self.parse, dont_filter=True)

    def parse(self, response):
        for index in range(1, 51):
            sel = response.xpath('//*[@id="questions"]/div[{index}]'.format(index=index))
            item = CrawlerItem()
            item['question'] = sel.xpath('div[2]/h3/a/text()').extract()[0]
            item['link'] = sel.xpath('div[2]/h3/a/@href').extract()[0]
            item['votes'] = sel.xpath('div[1]/div[1]/span[1]/text()').extract()[0]
            item['views'] = sel.xpath('div[1]/div[3]/span[1]/text()').extract()[0]
            item['_id'] = item['link'].split('/')[2]
            yield item

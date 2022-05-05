import urllib.request
import json
import sys
import time

def get_data(url):
    req = urllib.request.Request(
        url,
        data = None,
        headers = {
            'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.141 Safari/537.36',
            'Accept': '*/*',
            'DNT': "1",
            'Origin': 'https://www.nasdaq.com/',
            'Sec-Fetch-Mode': 'cors',
            'Connection': 'close'
        }
    )
    f = urllib.request.urlopen(req)
    data = f.read().decode('utf-8')
    return json.loads(data)

def get_dividends_for(symbol):
    dividends = get_data("https://api.nasdaq.com/api/quote/{0}/dividends?assetclass=stocks".format(symbol))
    stock = get_data("https://api.nasdaq.com/api/quote/{0}/info?assetclass=stocks".format(symbol))
    if ("data" in dividends) and (dividends["data"] is not None):
        print("%s,%s,$%s,%s" % (stock["data"]["symbol"], stock["data"]["primaryData"]["lastSalePrice"], dividends["data"]["annualizedDividend"], dividends["data"]["yield"]), flush = True)
    elif ("data" in stock) and (stock["data"] is not None):
        print("%s,%s,," % (stock["data"]["symbol"], stock["data"]["primaryData"]["lastSalePrice"]), flush = True)
    else:
        print("%s,,," % (symbol), flush = True)

def get_file_content(file):
    values = []
    for line in open(file):
        values.append(line.strip())
    return values

if len(sys.argv) > 1:
    file = sys.argv[1]
    symbols = get_file_content(file)
    for symbol in symbols:
        get_dividends_for(symbol)
        time.sleep(0.1)
else:
    print("Specify the file with symbols!")

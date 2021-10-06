def findMaxproduct(products):
    count = 0
    temp_count = 0
    temp_product = ""
    products.sort()
    for product in products:
        for product_temp in products:
            if product == product_temp:
                count += 1
        if count > temp_count:
            temp_product = product
            temp_count = count
        count = 0
    return temp_product



products =  ["greenShirt","YellowShirt","blueShirt","whiteShirt","blueShirt", "whiteShirt","greenShirt"]
print(findMaxproduct(products))

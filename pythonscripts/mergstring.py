def mergeString(str1, str2):
    newstr = ""
    i = 0
    while i < len(str1) and i < len(str2):
        newstr += str1[i]
        newstr += str2[i]
        i += 1
    return newstr


print(mergeString("ijk","ab"))


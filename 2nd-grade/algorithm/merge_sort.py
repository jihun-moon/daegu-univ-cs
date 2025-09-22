def merge_sort(a):
    if len(a) <= 1: return a
    m = len(a)//2
    L, R = merge_sort(a[:m]), merge_sort(a[m:])
    i=j=0; out=[]
    while i<len(L) and j<len(R):
        if L[i] <= R[j]:
            out.append(L[i]); i+=1
        else:
            out.append(R[j]); j+=1
    return out + L[i:] + R[j:]

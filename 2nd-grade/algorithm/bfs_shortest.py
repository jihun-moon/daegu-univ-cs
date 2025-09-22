from collections import deque, defaultdict

def bfs_shortest(n, edges, s):
    g = defaultdict(list)
    for u, v in edges:
        g[u].append(v); g[v].append(u)
    dist = [-1]*n; dist[s] = 0
    dq = deque([s])
    while dq:
        u = dq.popleft()
        for v in g[u]:
            if dist[v] == -1:
                dist[v] = dist[u] + 1
                dq.append(v)
    return dist

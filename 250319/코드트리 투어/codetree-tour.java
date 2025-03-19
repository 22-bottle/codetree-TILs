import java.io.*;
import java.util.*;

public class Main {
    public static class Data {
        int dest;
        int weight;

        public Data(int dest, int weight) {
            this.dest = dest;
            this.weight = weight;
        }
    }
    public static class Product {
        int id;
        int revenue;
        int dest;
        int real;

        public Product(int id, int revenue, int dest, int real) {
            this.id = id;
            this.revenue = revenue;
            this.dest = dest;
            this.real = real;
        }
    }

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();
    static int Q, N, M;
    static List<Data>[] adjList;
    static int[] dist;
    static Map<Integer, Product> products = new HashMap<>();
    static PriorityQueue<Product> best = new PriorityQueue<>((o1, o2) -> {
        if (o1.real == o2.real) return o1.id - o2.id;
        return o2.real - o1.real;
    });

    public static void main(String[] args) throws Exception {
        init();
        dijkstra(0);
        for (int i = 0; i < Q; i++) {
            st = new StringTokenizer(br.readLine());
            int command = Integer.parseInt(st.nextToken());
            if (command == 200) {
                create();
            } else if (command == 300) {
                int id = Integer.parseInt(st.nextToken());
                delete(id);
            } else if (command == 400) {
                sell();
            } else {
                change();
            }
        }
        System.out.print(sb.toString());
    }

    public static void change() {
        int origin = Integer.parseInt(st.nextToken());
        dijkstra(origin);
        best.clear();
        for (Map.Entry<Integer, Product> entry : products.entrySet()) {
            int id = entry.getKey();
            Product cur = entry.getValue();
            cur.real = cur.revenue - dist[cur.dest];
            if (dist[cur.dest] == Integer.MAX_VALUE) cur.real = -1;
            products.put(id, cur);
            best.offer(cur);
        }
    }

    public static void sell() {
        if (best.isEmpty()) {
            sb.append(-1).append("\n");
            return;
        }
        Product bestProduct = best.poll();
        while (!products.containsKey(bestProduct.id)) {
            if (best.isEmpty()) {
                sb.append(-1).append("\n");
                return;
            }
            bestProduct = best.poll();
        }
        if (bestProduct.real < 0) {
            best.offer(bestProduct);
            sb.append(-1).append("\n");
            return;
        }
        sb.append(bestProduct.id).append("\n");
        delete(bestProduct.id);
    }

    public static void delete(int id) {
        products.remove(id);
    }

    public static void create() {
        int id = Integer.parseInt(st.nextToken());
        int revenue = Integer.parseInt(st.nextToken());
        int dest = Integer.parseInt(st.nextToken());
        Product newProduct = new Product(id, revenue, dest, revenue - dist[dest]);
        if (dist[dest] == Integer.MAX_VALUE) newProduct.real = -1;
        products.put(id, newProduct);
        best.offer(newProduct);
    }

    public static void dijkstra(int s) {
        dist = new int[N];
        Arrays.fill(dist, Integer.MAX_VALUE);
        PriorityQueue<Data> pq = new PriorityQueue<>((o1, o2) -> {
            return o1.weight - o2.weight;
        });
        pq.offer(new Data(s, 0));
        while (!pq.isEmpty()) {
            Data cur = pq.poll();
            if (dist[cur.dest] < cur.weight) continue; 
            dist[cur.dest] = cur.weight;
            for (Data neighbor : adjList[cur.dest]) {
                if (dist[neighbor.dest] > cur.weight + neighbor.weight)
                    pq.offer(new Data(neighbor.dest, cur.weight + neighbor.weight));
            }
        }
    }

    public static void init() throws Exception {
        Q = Integer.parseInt(br.readLine()) - 1;
        st = new StringTokenizer(br.readLine());
        st.nextToken();
        N = Integer.parseInt(st.nextToken());
        adjList = new ArrayList[N];
        for (int i = 0; i < N; i++) {
            adjList[i] = new ArrayList<>();
        }
        M = Integer.parseInt(st.nextToken());
        for (int i = 0; i < M; i++) {
            int v = Integer.parseInt(st.nextToken());
            int u = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            adjList[v].add(new Data(u, w));
            adjList[u].add(new Data(v, w));
        }
    }
}
import java.util.*;

//PriorityQueue

class CheapestFlight3 {

    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
        if(flights == null || flights.length == 0 || K < 0) return -1;
        //build adj map
        Map<Integer, Map<Integer, Integer>> map = new HashMap<>();
        for(int[] flight : flights){
            map.putIfAbsent(flight[0], new HashMap<>());
            map.get(flight[0]).put(flight[1], flight[2]);
        }
        //priority queue
        PriorityQueue<City> pq = new PriorityQueue<>((a, b) -> (a.cost - b.cost));
        pq.add(new City(src, 0, -1));
        while(!pq.isEmpty()){
            City city = pq.poll();
            int cityName = city.name;
            int cityStop = city.stop;
            int cityCost = city.cost;
            if(cityStop > K){
                continue;
            }
            //下面一句：也不存在没遍历完，返回错误price的情况；因为如果之前有dst访问到了，但距离更大，肯定已经在pq后面了；最优结果到dst的，肯定前面中间节点的price也是比dst不优解小的
            if(cityName == dst){//重点：因为pq已经将cost小的放前面了，所以只要遇见dst，肯定是正确结果，minPrice
                return cityCost;
            }
            if(map.containsKey(cityName)){
                for(int nei : map.get(cityName).keySet()){
                    pq.add(new City(nei, city.cost + map.get(cityName).get(nei), city.stop + 1));
                }
            }

        }
        return -1;
    }
}

//BFS
class City{
    int name;
    int cost; //cost from src
    int stop; //stop from src

    public City(int name, int cost, int stop) {
        this.name = name;
        this.cost = cost;
        this.stop = stop;
    }

}

class CheapestFlight2 {
    int minPrice = Integer.MAX_VALUE;
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
        if(flights == null || flights.length == 0 || K < 0) return -1;
        Map<Integer, Map<Integer, Integer>> map = new HashMap<>();
        for(int[] flight : flights){
            map.putIfAbsent(flight[0], new HashMap<>());
            map.get(flight[0]).put(flight[1], flight[2]);
        }
        bfs(map, src, dst, K);
        return minPrice == Integer.MAX_VALUE ? -1 : minPrice;
    }

    public void bfs(Map<Integer, Map<Integer, Integer>> map, int src, int dst, int K){
        Queue<City> queue = new LinkedList<>();
        //Set<Integer> set = new HashSet<>();
        queue.offer(new City(src, 0, -1));

        while(!queue.isEmpty()){
            City city = queue.poll();
            int cityName = city.name;
            int cityStop = city.stop;

            if(cityStop == K + 1) return;
            if(city.cost > minPrice) continue;

            //set.add(cityName);

            if(cityName == dst){
                minPrice = Math.min(minPrice, city.cost);
            }

            if(map.containsKey(cityName)){
                for(int nei : map.get(cityName).keySet()){
                    //if(set.contains(nei)) continue;
                    queue.offer(new City(nei, city.cost + map.get(cityName).get(nei), city.stop + 1));
                }
            }
        }

    }
}

//DFS
class CheapestFlight {
    int minPrice = Integer.MAX_VALUE;
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
        if(flights == null || flights.length == 0 || K < 0) return -1;
        Map<Integer, Map<Integer, Integer>> map = new HashMap<>();
        for(int[] flight : flights){
            map.putIfAbsent(flight[0], new HashMap<>());
            map.get(flight[0]).put(flight[1], flight[2]);
        }
        boolean[] visited = new boolean[n];

        dfs(map, src, dst, K, -1, 0, visited);
        return minPrice == Integer.MAX_VALUE ? -1 : minPrice;
    }

    public void dfs(Map<Integer, Map<Integer, Integer>> map, int start, int dst, int K, int stop,
                    int price, boolean[] visited){

        if(stop == K + 1){
            return;
        }
        if(start == dst){
            if(stop <= K){
                minPrice = Math.min(minPrice, price);
            }
        }
        if(map.containsKey(start)){
            for (int nei : map.get(start).keySet()) {
                Map<Integer, Integer> neighbors = map.getOrDefault(start, new HashMap<>());
                if(!visited[nei]){
                    visited[nei] = true;
                    dfs(map, nei, dst, K, stop + 1, price + neighbors.getOrDefault(nei, 0), visited);
                    visited[nei] = false;
                }
            }
        }

    }

    public static void main(String[] args){
        CheapestFlight c1 = new CheapestFlight();
        CheapestFlight2 c2 = new CheapestFlight2();
        CheapestFlight3 c3 = new CheapestFlight3();

        //int[][] flights = {{0, 1, 100}, {0, 2, 500}, {1, 2, 100}};
        int[][] flights = {{0,1,5},{1,2,5},{0,3,2},{3,1,2},{1,4,1},{4,2,1}};
        //int[][] flights ={{0,12,28},{5,6,39},{8,6,59},{13,15,7},{13,12,38},{10,12,35},{15,3,23},{7,11,26},{9,4,65},{10,2,38},{4,7,7},{14,15,31},{2,12,44},{8,10,34},{13,6,29},{5,14,89},{11,16,13},{7,3,46},{10,15,19},{12,4,58},{13,16,11},{16,4,76},{2,0,12},{15,0,22},{16,12,13},{7,1,29},{7,14,100},{16,1,14},{9,6,74},{11,1,73},{2,11,60},{10,11,85},{2,5,49},{3,4,17},{4,9,77},{16,3,47},{15,6,78},{14,1,90},{10,5,95},{1,11,30},{11,0,37},{10,4,86},{0,8,57},{6,14,68},{16,8,3},{13,0,65},{2,13,6},{5,13,5},{8,11,31},{6,10,20},{6,2,33},{9,1,3},{14,9,58},{12,3,19},{11,2,74},{12,14,48},{16,11,100},{3,12,38},{12,13,77},{10,9,99},{15,13,98},{15,12,71},{1,4,28},{7,0,83},{3,5,100},{8,9,14},{15,11,57},{3,6,65},{1,3,45},{14,7,74},{2,10,39},{4,8,73},{13,5,77},{10,0,43},{12,9,92},{8,2,26},{1,7,7},{9,12,10},{13,11,64},{8,13,80},{6,12,74},{9,7,35},{0,15,48},{3,7,87},{16,9,42},{5,16,64},{4,5,65},{15,14,70},{12,0,13},{16,14,52},{3,10,80},{14,11,85},{15,2,77},{4,11,19},{2,7,49},{10,7,78},{14,6,84},{13,7,50},{11,6,75},{5,10,46},{13,8,43},{9,10,49},{7,12,64},{0,10,76},{5,9,77},{8,3,28},{11,9,28},{12,16,87},{12,6,24},{9,15,94},{5,7,77},{4,10,18},{7,2,11},{9,5,41}};

        //System.out.println(c1.findCheapestPrice(3, flights, 0, 2, 1));
        System.out.println(c2.findCheapestPrice(5, flights, 0, 2, 2));
        //System.out.println(c2.findCheapestPrice(17, flights, 13, 4, 13));
        System.out.println(c3.findCheapestPrice(5, flights, 0, 2, 2));


    }
}

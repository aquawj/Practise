import java.util.*;

public class AllienDictionary {
    /**
    BFS 最优解*/
    public String alienOrder(String[] words) {
        Map<Character, Set<Character>> map=new HashMap<>();
        Map<Character, Integer> degree=new HashMap<>();
        String result="";
        if(words==null || words.length==0) return result;

        // 构建并初始化degree map
        for(String s: words){
            for(char c: s.toCharArray()){
                degree.put(c,0);
            }
        }

        // 构建关系Graph map，更新degree map
        for(int i=0; i<words.length-1; i++){
            String cur=words[i];
            String next=words[i+1];
            int length=Math.min(cur.length(), next.length());
            for(int j=0; j<length; j++){
                char c1=cur.charAt(j);
                char c2=next.charAt(j);
                if(c1!=c2){
                    Set<Character> set=new HashSet<Character>();
                    if(map.containsKey(c1)) set=map.get(c1);
                    if(!set.contains(c2)){
                        set.add(c2);
                        map.put(c1, set);
                        degree.put(c2, degree.get(c2)+1);
                    }
                    break;
                }
            }
        }

        // Queue 用于拓扑排序的输出结果
        Queue<Character> q=new LinkedList<Character>();
        for(char c: degree.keySet()){
            if(degree.get(c)==0) q.add(c);
        }
        while(!q.isEmpty()){
            char c=q.remove();
            result+=c;
            if(map.containsKey(c)){
                for(char c2: map.get(c)){
                    degree.put(c2,degree.get(c2)-1);
                    if(degree.get(c2)==0) q.add(c2);
                }
            }
        }
        //这种情况，无法得出一致的关系，返回空
        if(result.length()!=degree.size()) return "";
        return result;
    }

    /**
     DFS*/
    public String alienOrder_dfs(String[] words) {
        //build graph
        Map<Integer,Set<Integer>> adj = new HashMap<>();
        int[] state = new int[26]; // 0 initiate
        StringBuilder sb = new StringBuilder();

        for(String s: words){
            for(char c: s.toCharArray()){
                state[c-'a'] = 1; // 1 exist
            }
        }

        for(int i = 0; i < words.length -1; i++){
            String cur = words[i];
            String nxt = words[i+1];
            int length = Math.min(cur.length(),nxt.length());
            for(int j = 0; j < length; j++){
                int a = cur.charAt(j)-'a';
                int b = nxt.charAt(j)-'a';
                Set<Integer> temp = new HashSet<>();
                if (a!=b){
                    if (adj.containsKey(a)) temp = adj.get(a);
                    if(!temp.contains(b)){
                        temp.add(b);
                        adj.put(a,temp);
                    }
                    break;
                }
            }
            if(cur.length() > nxt.length() && nxt.equals(cur.substring(0,nxt.length())))
                return "";
        }

        for(int i = 0; i < 26; i++){
            if(state[i] == 1) {
                if (!dfs(i,sb,state,adj)) return "";
            }
        }
        return sb.reverse().toString();
    }


    public boolean dfs(int i,StringBuilder sb,int[] state, Map<Integer, Set<Integer>> adj){
        state[i] = 2; // 2 visiting
        if(adj.containsKey(i)){
            for(Integer i2 : adj.get(i)){
                if(state[i2] == 2) return false;
                if(state[i2]== 1 && !dfs(i2,sb,state, adj)) return false;
            }
        }
        sb.append((char)(i+'a'));
        state[i]=3;
        return true;
    }

    //类似题：preference list
//    每个人都有一个preference的排序，在不违反每个人的 preference的情况下
//    得到整体的preference的排序
//    input-> [[3, 5, 7, 9], [2, 3, 8], [5, 8]]
//    output-> [2, 3, 5, 8, 7, 9]

    public List<Integer> getPreference(List<List<Integer>> preferences) {

        Map<Integer, Integer> inDegree = new HashMap<>();
        Map<Integer, Set<Integer>> nodes = new HashMap<>();

        for (List<Integer> l : preferences) {
            for (int i = 0; i < l.size() - 1; i++) {
                int from = l.get(i);
                int to = l.get(i + 1);

                if (!nodes.containsKey(from)) {
                    inDegree.put(from, 0);
                    nodes.put(from, new HashSet<>());
                }

                if (!nodes.containsKey(to)) {
                    inDegree.put(to, 0);
                    nodes.put(to, new HashSet<>());
                }

                if (!nodes.get(from).contains(to)) {
                    Set<Integer> s = nodes.get(from);
                    s.add(to);
                    nodes.put(from, s);
                }

                inDegree.put(to, inDegree.getOrDefault(to, 0) + 1);
            }
        }

        Queue<Integer> q = new LinkedList<>();

        for (int k : inDegree.keySet()) {
            if (inDegree.get(k) == 0) {
                q.offer(k);
            }
        }

        List<Integer> res = new ArrayList<>();

        while (!q.isEmpty()) {
            int id = q.poll();
            res.add(id);
            Set<Integer> neighbors = nodes.get(id);
            for (int next : neighbors) {
                int degree = inDegree.get(next) - 1;
                inDegree.put(next, degree);
                if (degree == 0) q.offer(next);
            }
        }
        return res;
    }
}

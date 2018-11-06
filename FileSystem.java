import java.util.HashMap;
import java.util.Map;

/*Write a file system class, which has two functions: create, and get
        * create("/a",1)
        * get("/a") //get 1
        * create("/a/b",2)
        * get("/a/b") //get 2
        * 注意下面两个例子：
        * create("/c/d",1) //Error, because "/c" is not existed
        * get("/c") //Error, because "/c" is not existed
 follow up 是写一个watch 函数，比如watch("/a",new Runnable(){System.out.println("helloword");})
后，每当create("/a/b"，1) 等在/a 之下的目录不产生error 的话，都会执行在“/a”上的callback函数
        比如  watch("/a",System.out.println("yes"))
             watch("/a/b",System.out.println("no"))
             当create("/a/b/c",1)时，两个 callback 函数都会被触发，会output yes 和no
        * */

// HashMap 和 Trie 两种方法：
public class FileSystem {

    Map<String, Integer> pathMap;
    Map<String, Runnable> callbackMap;

    public FileSystem(){
        pathMap = new HashMap<>();
        callbackMap = new HashMap<>();
        pathMap.put("", 0); // root path
    }

    public boolean create(String path, int value){
        // 已经存在，无法创建
        if(pathMap.containsKey(path)){
            return false;
        }
        // 父路径不存在，无法创建（例子里是这样的）
        int index = path.lastIndexOf("/");
        String father = path.substring(0, index);
        if(!pathMap.containsKey(father)){
            return false;
        }
        // 放入map
        pathMap.put(path, value);
        // trigger callback
        trigger(path);
        return true;
    }

    public int get(String path){
        return pathMap.getOrDefault(path, -1); //原代码没写-1
    }

    // 有的面经需要写set：将path的叶子节点set为value
    public boolean set(String path, int value) {
        if (!pathMap.containsKey(path)) {
            return false;
        }
        pathMap.put(path, value);
// Trigger callbacks
        trigger(path);
        return true;
    }

    public boolean watch(String path, Runnable callback) {
        if (!pathMap.containsKey(path)) {
            return false;
        }
        callbackMap.put(path, callback);
        return true;
    }

    public void trigger(String path){
        // Trigger callbacks
        String curPath = path;
        while (curPath.length() > 0) {
            if (callbackMap.containsKey(curPath)) {
                callbackMap.get(curPath).run(); //不能退出，继续看父路径
            }
            int index = path.lastIndexOf("/");
            curPath = curPath.substring(0, index);
        }
    }

}

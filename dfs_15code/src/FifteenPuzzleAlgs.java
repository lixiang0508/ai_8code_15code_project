import java.util.*;

/**
 * @author zlx
 * @create 2021-11-07 8:53 下午
 */
public class FifteenPuzzleAlgs {
    private FifteenPuzzle start_ep;
    private FifteenPuzzle target_ep;
    private int[] startNums = new int[16] ;
    private int[] targetNums = new int[16] ;
    private int depth=0;
    private Stack<FifteenPuzzle> ep_stack = new Stack<FifteenPuzzle>();
    private LinkedList<FifteenPuzzle> searched_list = new LinkedList<FifteenPuzzle>();
    private Queue<FifteenPuzzle> ep_queue = new LinkedList<FifteenPuzzle>();
    private int[]   array = new int[16];
    private int[]    target = new int[16];
    public FifteenPuzzleAlgs() {
        ep_stack.clear();
        ep_queue.clear() ;
        searched_list.clear() ;
        Scanner scanner = new Scanner(System.in) ;
        System.out.println("请输入初始位置（其中输入0代表空白块，例如：2 3 4 5）：");
        for (int i = 0; i < 16; i++) {

            array[i] = scanner.nextInt();
        }

        // 输入目标位置
        System.out.println("请输入目标位置（其中输入0代表空白块，例如：2 3 4 5）：");

        for (int i = 0; i < 16; i++) {

            target[i] = scanner.nextInt();

        }
        scanner.close();
        start_ep = new FifteenPuzzle(array) ;
        start_ep.setDepth(depth);
        ep_stack.push(start_ep) ;

        target_ep = new FifteenPuzzle(target) ;
        ep_queue.offer(start_ep);


    }
    public void ai_search2() {
        System.out.println("使用曼哈顿距离作为启发函数！");
        ArrayList<FifteenPuzzle> open = new ArrayList<>() ;
        ArrayList<FifteenPuzzle> close = new ArrayList<>() ;
        long startTime = System.currentTimeMillis();//开始时间

        start_ep.init2(target_ep);
        open.add(start_ep);
        int stepCount=0;
        while(!open.isEmpty()) {
            Collections.sort(open);            //按照evaluation的值排序
            FifteenPuzzle best = open.get(0);    //从open表中取出最小估值的状态并移除出open表
            open.remove(0);
            close.add(best);

            if(best.isTarget(target_ep)){
                //输出
                //best.printRoute();
                best.print();
                System.out.println("找到了,花费"+stepCount+"步");
                long end=System.currentTimeMillis(); //获取结束时间
                System.out.println("程序运行时间： "+(end-startTime)+"ms");
                System.exit(0);
            }  else {
                FifteenPuzzle tmp= best.deepCopy();
                // System.out.print("   当前步数"+stepCount);
                int idx= tmp.getPosition();
                if(FifteenPuzzleOp.canMove(idx,0)){
                    //可以向上移动
                    FifteenPuzzle nxt=FifteenPuzzleOp.movePos(best,0);
                    stepCount++;
                    nxt.print();
                    nxt.operation2(open,close,best,target_ep);
                }
                if(FifteenPuzzleOp.canMove(idx,1)) {
                    //可以向下移动
                    FifteenPuzzle nxt=FifteenPuzzleOp.movePos(best,1);
                    stepCount++;
                    nxt.print();
                    nxt.operation2(open,close,best,target_ep);

                }
                if(FifteenPuzzleOp.canMove(idx,2)){
                    //可以向左移动
                    FifteenPuzzle nxt=FifteenPuzzleOp.movePos(best,2);
                    stepCount++;
                    nxt.print();
                    nxt.operation2(open,close,best,target_ep);
                }
                if(FifteenPuzzleOp.canMove(idx,3)){
                    //可以向右移动
                    FifteenPuzzle nxt=FifteenPuzzleOp.movePos(best,3);
                    stepCount++;
                    nxt.print();
                    nxt.operation2(open,close,best,target_ep);
                }

            }
        }

    }
    public void ai_search1() {
        System.out.println("使用错位个数作为启发函数！");
        ArrayList<FifteenPuzzle> open = new ArrayList<>() ;
        ArrayList<FifteenPuzzle> close = new ArrayList<>() ;
        long startTime = System.currentTimeMillis();//开始时间

        start_ep.init1(target_ep);
        open.add(start_ep);
        int stepCount=0;
        while(!open.isEmpty()) {
            Collections.sort(open);            //按照evaluation的值排序
            FifteenPuzzle best = open.get(0);    //从open表中取出最小估值的状态并移除出open表
            open.remove(0);
            close.add(best);

            if(best.isTarget(target_ep)){
                //输出
                //best.printRoute();
                best.print();
                System.out.println("找到了,花费"+stepCount+"步");
                long end=System.currentTimeMillis(); //获取结束时间
                System.out.println("程序运行时间： "+(end-startTime)+"ms");
                System.exit(0);
            }  else {
                FifteenPuzzle tmp= best.deepCopy();
                // System.out.print("   当前步数"+stepCount);
                int idx= tmp.getPosition();
                if(FifteenPuzzleOp.canMove(idx,0)){
                    //可以向上移动
                    FifteenPuzzle nxt=FifteenPuzzleOp.movePos(best,0);
                    stepCount++;
                    nxt.print();
                    nxt.operation1(open,close,best,target_ep);
                }
                if(FifteenPuzzleOp.canMove(idx,1)) {
                    //可以向下移动
                    FifteenPuzzle nxt=FifteenPuzzleOp.movePos(best,1);
                    stepCount++;
                    nxt.print();
                    nxt.operation1(open,close,best,target_ep);

                }
                if(FifteenPuzzleOp.canMove(idx,2)){
                    //可以向左移动
                    FifteenPuzzle nxt=FifteenPuzzleOp.movePos(best,2);
                    stepCount++;
                    nxt.print();
                    nxt.operation1(open,close,best,target_ep);
                }
                if(FifteenPuzzleOp.canMove(idx,3)){
                    //可以向右移动
                    FifteenPuzzle nxt=FifteenPuzzleOp.movePos(best,3);
                    stepCount++;
                    nxt.print();
                    nxt.operation1(open,close,best,target_ep);
                }

            }
        }

    }
    public void bfs(){
        //广度优先搜索
        searched_list.clear();
        while(!ep_queue.isEmpty()){
            FifteenPuzzle move_ep = ep_queue.poll();
            depth= move_ep.getDepth();
            move_ep.print();
            searched_list.add(move_ep) ;
            if(move_ep.isEquals(target_ep)) {
                System.out.println("找到了！");
                System.out.println("花费了"+depth+"步");
                return;
            }
            depth++;
            FifteenPuzzle temp = null;
            temp= move_ep.deepCopy();
            int idx= temp.getPosition();
            if(FifteenPuzzleOp.canMove(idx,0)) {
                //向上移动
                temp=FifteenPuzzleOp.movePos(move_ep,0)  ;

                temp.setDepth(depth);
                if(temp.isEquals(target_ep)) {
                    System.out.println("找到了！");
                    System.out.println("花费了"+depth+"步");
                    return;
                }

                if(!searched_list.contains(temp)){
                    ep_queue.offer(temp);
                }
            }
            if(FifteenPuzzleOp.canMove(idx,1)) {
                //向下移动
                temp=FifteenPuzzleOp.movePos(move_ep,1)  ;
                temp.setDepth(depth);
                if(temp.isEquals(target_ep)) {
                    System.out.println("找到了！");
                    System.out.println("花费了"+depth+"步");
                    return;
                }
                if(!searched_list.contains(temp)){
                    ep_queue.offer(temp);
                }
            }
            if(FifteenPuzzleOp.canMove(idx,2)) {
                //向左移动
                temp=FifteenPuzzleOp.movePos(move_ep,2)  ;
                temp.setDepth(depth);
                if(temp.isEquals(target_ep)) {
                    System.out.println("找到了！");
                    System.out.println("花费了"+depth+"步");
                    return;
                }
                if(!searched_list.contains(temp)){
                    ep_queue.offer(temp);
                }
            }
            if(FifteenPuzzleOp.canMove(idx,3)) {
                //向上移动
                temp=FifteenPuzzleOp.movePos(move_ep,3)  ;
                temp.setDepth(depth);
                if(temp.isEquals(target_ep)) {
                    System.out.println("找到了！");
                    System.out.println("花费了"+depth+"步");
                    return;
                }
                if(!searched_list.contains(temp)){
                    ep_queue.offer(temp);
                }
            }
        }
        System.out.println("目标未找到！");


    }



    public void dfs() {
        //深度优先搜索
        searched_list.clear();
        while(!ep_stack.isEmpty()) {
            FifteenPuzzle move_ep= ep_stack.pop() ;
            depth= move_ep.getDepth();
            move_ep.print();
            searched_list.add(move_ep) ;
            if(move_ep.isEquals(target_ep)) {
                System.out.println("找到了！");
                System.out.println("花费了"+depth+"步");
                return;
            }
            depth++;
            FifteenPuzzle temp = null;
            temp= move_ep.deepCopy();
            int idx= temp.getPosition();
            if(FifteenPuzzleOp.canMove(idx,0)) {
                //向上移动
                temp=FifteenPuzzleOp.movePos(move_ep,0)  ;

                temp.setDepth(depth);
                if(temp.isEquals(target_ep)) {
                    System.out.println("找到了！");
                    System.out.println("花费了"+depth+"步");
                    return;
                }

                if(!searched_list.contains(temp)){
                    ep_stack.push(temp);
                }
            }
            if(FifteenPuzzleOp.canMove(idx,1)) {
                //向下移动
                temp=FifteenPuzzleOp.movePos(move_ep,1)  ;
                temp.setDepth(depth);
                if(temp.isEquals(target_ep)) {
                    System.out.println("找到了！");
                    System.out.println("花费了"+depth+"步");
                    return;
                }
                if(!searched_list.contains(temp)){
                    ep_stack.push(temp);
                }
            }
            if(FifteenPuzzleOp.canMove(idx,2)) {
                //向左移动
                temp=FifteenPuzzleOp.movePos(move_ep,2)  ;
                temp.setDepth(depth);
                if(temp.isEquals(target_ep)) {
                    System.out.println("找到了！");
                    System.out.println("花费了"+depth+"步");
                    return;
                }
                if(!searched_list.contains(temp)){
                    ep_stack.push(temp);
                }
            }
            if(FifteenPuzzleOp.canMove(idx,3)) {
                //向上移动
                temp=FifteenPuzzleOp.movePos(move_ep,3)  ;
                temp.setDepth(depth);
                if(temp.isEquals(target_ep)) {
                    System.out.println("找到了！");
                    System.out.println("花费了"+depth+"步");
                    return;
                }
                if(!searched_list.contains(temp)){
                    ep_stack.push(temp);
                }
            }
        }
        System.out.println("目标未找到！");
    }


    public static void main(String[] args) {
        FifteenPuzzleAlgs fpa = new FifteenPuzzleAlgs();
         //fpa.dfs();
        //fpa.bfs();
        //fpa.ai_search1();
        fpa.ai_search2();
    }
}

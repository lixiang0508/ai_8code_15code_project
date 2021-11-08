import java.util.*;

/**
 * @author zlx
 * @create 2021-10-23 6:10 下午
 */
public class EightPuzzleAlgs {
    private EightPuzzle start_ep;
    private EightPuzzle target_ep;
    private int[] startNums = new int[9] ;
    private int[] targetNums = new int[9] ;
    private int depth=0;
    private Stack<EightPuzzle> ep_stack = new Stack<EightPuzzle>();
    private LinkedList<EightPuzzle> searched_list = new LinkedList<EightPuzzle>();
    private Queue<EightPuzzle> ep_queue = new LinkedList<EightPuzzle>();
      private int[]   array = new int[9];
      private int[]    target = new int[9];
    public EightPuzzleAlgs() {
        ep_stack.clear();
        ep_queue.clear() ;
        searched_list.clear() ;
              Scanner scanner = new Scanner(System.in) ;
        System.out.println("请输入初始位置（其中输入0代表空白块，例如：2 8 3 1 0 4 7 6 5）：");
        for (int i = 0; i < 9; i++) {
            
                array[i] = scanner.nextInt();
            }

        // 输入目标位置
        System.out.println("请输入目标位置（其中输入0代表空白块，例如：2 8 3 1 4 0 7 6 5）：");

        for (int i = 0; i < 9; i++) {

                target[i] = scanner.nextInt();

        }
        scanner.close();
        start_ep = new EightPuzzle(array) ;
        start_ep.setDepth(depth);
        ep_stack.push(start_ep) ;

        target_ep = new EightPuzzle(target) ;
        ep_queue.offer(start_ep);
        /*
        if(!start_ep.isSolvable(target_ep)) {
            System.out.println("此题无解");
            System.exit(0) ;
        }    */


    }
    public void ai_search2() {
        System.out.println("使用曼哈顿距离作为启发函数！");
        ArrayList<EightPuzzle> open = new ArrayList<>() ;
        ArrayList<EightPuzzle> close = new ArrayList<>() ;
        long startTime = System.currentTimeMillis();//开始时间

        start_ep.init2(target_ep);
        open.add(start_ep);
        int stepCount=0;
        while(!open.isEmpty()) {
            Collections.sort(open);            //按照evaluation的值排序
            EightPuzzle best = open.get(0);    //从open表中取出最小估值的状态并移除出open表
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
                EightPuzzle tmp= best.deepCopy();
                // System.out.print("   当前步数"+stepCount);
                int idx= tmp.getPosition();
                if(EightPuzzleOp.canMove(idx,0)){
                    //可以向上移动
                    EightPuzzle nxt=EightPuzzleOp.movePos(best,0);
                    stepCount++;
                    nxt.print();
                    nxt.operation2(open,close,best,target_ep);
                }
                if(EightPuzzleOp.canMove(idx,1)) {
                    //可以向下移动
                    EightPuzzle nxt=EightPuzzleOp.movePos(best,1);
                    stepCount++;
                    nxt.print();
                    nxt.operation2(open,close,best,target_ep);

                }
                if(EightPuzzleOp.canMove(idx,2)){
                    //可以向左移动
                    EightPuzzle nxt=EightPuzzleOp.movePos(best,2);
                    stepCount++;
                    nxt.print();
                    nxt.operation2(open,close,best,target_ep);
                }
                if(EightPuzzleOp.canMove(idx,3)){
                    //可以向右移动
                    EightPuzzle nxt=EightPuzzleOp.movePos(best,3);
                    stepCount++;
                    nxt.print();
                    nxt.operation2(open,close,best,target_ep);
                }

            }
        }

    }
    public void ai_search1()  {
        //用错位数进行估计 启发函数1
        //target_ep 是目标八数码

        /*
        int mis=0;
        for(int i=0;i<9;i++) {
            if(start_ep.data[i]!=target_ep.data[i]){
                   mis++;
            }
        }
        start_ep.setMispostion(mis);   //当前错位数
        start_ep.setEvaluation(depth+mis); //当前总成本估计
        start_ep.setParent(null); //第一个节点 没有父节点
        */
        ArrayList<EightPuzzle> open = new ArrayList<>() ;
        ArrayList<EightPuzzle> close = new ArrayList<>() ;
        long startTime = System.currentTimeMillis();//开始时间

        start_ep.init1(target_ep);
        open.add(start_ep);
        int stepCount=0;
        while(!open.isEmpty()) {
            Collections.sort(open);            //按照evaluation的值排序
            EightPuzzle best = open.get(0);    //从open表中取出最小估值的状态并移除出open表
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
                EightPuzzle tmp= best.deepCopy();
               // System.out.print("   当前步数"+stepCount);
                int idx= tmp.getPosition();
                if(EightPuzzleOp.canMove(idx,0)){
                    //可以向上移动
                    EightPuzzle nxt=EightPuzzleOp.movePos(best,0);
                    stepCount++;
                    nxt.print();
                    nxt.operation1(open,close,best,target_ep);
                }
                if(EightPuzzleOp.canMove(idx,1)) {
                    //可以向下移动
                    EightPuzzle nxt=EightPuzzleOp.movePos(best,1);
                    stepCount++;
                    nxt.print();
                    nxt.operation1(open,close,best,target_ep);

                }
                if(EightPuzzleOp.canMove(idx,2)){
                    //可以向左移动
                    EightPuzzle nxt=EightPuzzleOp.movePos(best,2);
                    stepCount++;
                    nxt.print();
                    nxt.operation1(open,close,best,target_ep);
                }
                if(EightPuzzleOp.canMove(idx,3)){
                    //可以向右移动
                    EightPuzzle nxt=EightPuzzleOp.movePos(best,3);
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
        EightPuzzle move_ep = ep_queue.poll();
            depth= move_ep.getDepth();
            move_ep.print();
            searched_list.add(move_ep) ;
            if(move_ep.isEquals(target_ep)) {
                System.out.println("找到了！");
                System.out.println("花费了"+depth+"步");
                return;
            }
            depth++;
            EightPuzzle temp = null;
            temp= move_ep.deepCopy();
            int idx= temp.getPosition();
            if(EightPuzzleOp.canMove(idx,0)) {
                //向上移动
                temp=EightPuzzleOp.movePos(move_ep,0)  ;

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
            if(EightPuzzleOp.canMove(idx,1)) {
                //向下移动
                temp=EightPuzzleOp.movePos(move_ep,1)  ;
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
            if(EightPuzzleOp.canMove(idx,2)) {
                //向左移动
                temp=EightPuzzleOp.movePos(move_ep,2)  ;
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
            if(EightPuzzleOp.canMove(idx,3)) {
                //向上移动
                temp=EightPuzzleOp.movePos(move_ep,3)  ;
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
            EightPuzzle move_ep= ep_stack.pop() ;
            depth= move_ep.getDepth();
            move_ep.print();
            searched_list.add(move_ep) ;
            if(move_ep.isEquals(target_ep)) {
                System.out.println("找到了！");
                System.out.println("花费了"+depth+"步");
                return;
            }
            depth++;
            EightPuzzle temp = null;
            temp= move_ep.deepCopy();
            int idx= temp.getPosition();
            if(EightPuzzleOp.canMove(idx,0)) {
                //向上移动
                temp=EightPuzzleOp.movePos(move_ep,0)  ;

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
            if(EightPuzzleOp.canMove(idx,1)) {
                //向下移动
                temp=EightPuzzleOp.movePos(move_ep,1)  ;
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
            if(EightPuzzleOp.canMove(idx,2)) {
                //向左移动
                temp=EightPuzzleOp.movePos(move_ep,2)  ;
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
            if(EightPuzzleOp.canMove(idx,3)) {
                //向上移动
                temp=EightPuzzleOp.movePos(move_ep,3)  ;
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
        EightPuzzleAlgs epa = new EightPuzzleAlgs();
       // epa.dfs();
        //epa.bfs();
       // epa.ai_search1();
        epa.ai_search2();
    }
}

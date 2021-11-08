import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author zlx
 * @create 2021-10-23 5:40 下午
 */
public class EightPuzzle implements  Cloneable,Comparable {

    //利用一维数组存储数据
    public int[]  data;
    private int depth;
    private int zeroPos;//0 索引
    /*以下是新加的属性
    * */
    private int mispostion; //用启发式算法计算 分为两种 错位个数以及曼哈顿距离
    private int evaluation; //depth+misposition
    private EightPuzzle parent;//父结点

    public int getMispostion() {
        return mispostion;
    }

    public void setMispostion(int mispostion) {
        this.mispostion = mispostion;
    }

    public int getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(int evaluation) {
        this.evaluation = evaluation;
    }

    public EightPuzzle getParent() {
        return parent;
    }

    public void setParent(EightPuzzle parent) {
        this.parent = parent;
    }

    public int[] getData() {
        return data;
    }

    public void setData(int[] data) {
        this.data = data;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }



    public void setZeroPos(int zeroPos) {
        this.zeroPos = zeroPos;
    }

    public EightPuzzle() {
        data=new int[9]   ;
    }

    public EightPuzzle(int[] data) {
        this.data = data;
    }
    public boolean isEquals(EightPuzzle other) {
        return Arrays.equals(this.data,other.data);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer(20);
        for (int i = 0; i < 9; i++){
            sb.append(this.data[i]);sb.append(" ");
        }
        return sb.toString();
    }
    //获取0所在位置
    public int getPosition() {
        for(int i=0;i<9;i++) {
            if(data[i]==0) {
                return i;
            }
        }
        return -1;
    }
    public void print(){
        System.out.println(this.toString());
    }
    @Override    //浅拷贝
    protected EightPuzzle clone() throws CloneNotSupportedException {
        // TODO Auto-generated method stub
        return new EightPuzzle(Arrays.copyOf(this.data, this.data.length));
    }
    public EightPuzzle deepCopy()  {
        EightPuzzle tmp= new EightPuzzle();
        for (int i = 0; i < 9; i++) {
            tmp.data[i] =this.data[i];

        }
        tmp.zeroPos=this.zeroPos;
        tmp.depth=this.depth;
        tmp.mispostion=this.mispostion;
        tmp.evaluation=this.evaluation;
        tmp.parent=this.parent;
        return tmp;

    }
    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        return this.isEquals((EightPuzzle)obj);
        //&&(this.getDepth() == ((EightPuzzle)obj).getDepth()
    }
    //是否存在于open表中
    public void init1(EightPuzzle target) {
        //启发函数1 初始化
        int mis=0;
        for(int i=0;i<9;i++) {
            if(this.data[i]!=target.data[i]){
                mis++;
            }
        }
        this.setMispostion(mis);
        if(this.getParent()==null)   {
            this.setDepth(0);
        }  else{
            this.setDepth(this.getParent().getDepth()+1);
        }
        this.evaluation=this.depth+this.depth;//设置总成本
    }

    public void init2(EightPuzzle target){
        //init2 曼哈顿距离初始化
        this.setMispostion(this.mhtdis(target));
        if(this.getParent()==null){
            this.setDepth(0);
        }else{
            this.depth = this.parent.getDepth()+1;
        }
        this.setEvaluation(this.getDepth()+this.getMispostion());
    }
    //在open表里是否存在
    public int isExists(ArrayList<EightPuzzle> open){
        for(int i=0;i<open.size();i++) {
            if(Arrays.equals(open.get(i).getData(),getData())) {
                return i;
            }
        }

        return -1;
    }
    public int getIdx(int[] arr, int tgt)  {
        for(int i=0;i<arr.length;i++){
            if(arr[i]==tgt) {
                return i;
            }
        }
        return -1;
    }
    public int mhtdis(EightPuzzle target)  {    //计算曼哈屯距离作为启发函数
        int[]  tgtNum  =target.getData();
        int[]    curNum =this.getData();
        int res=0;
        for(int i=1;i<9;i++) {
            res+=Math.abs(getIdx(curNum,i)-getIdx(tgtNum,i))  ;
        }
        return res;

    }





    public void operation1(ArrayList<EightPuzzle> open,ArrayList<EightPuzzle> close,EightPuzzle parent,EightPuzzle target){
        if(this.isExists(close) == -1){
            int position = this.isExists(open);
            if(position == -1){
                this.parent = parent;
                this.init1(target);
                open.add(this);
            }else{
                if(this.getDepth() < open.get(position).getDepth()){
                    open.remove(position);
                    this.parent = parent;
                    this.init1(target);
                    open.add(this);
                }
            }
        }
    }
    public void operation2(ArrayList<EightPuzzle> open,ArrayList<EightPuzzle> close,EightPuzzle parent,EightPuzzle target){
        if(this.isExists(close) == -1){
            int position = this.isExists(open);
            if(position == -1){
                this.parent = parent;
                this.init2(target);
                open.add(this);
            }else{
                if(this.getDepth() < open.get(position).getDepth()){
                    open.remove(position);
                    this.parent = parent;
                    this.init2(target);
                    open.add(this);
                }
            }
        }
    }

    @Override
    public int compareTo(Object o) {
        EightPuzzle c = (EightPuzzle) o;
        return this.evaluation-c.getEvaluation();//默认排序为f(n)由小到大排序
    }
    public boolean isTarget(EightPuzzle target){
        return Arrays.equals(getData(), target.getData());
    }
    public boolean isSolvable(EightPuzzle target){
        int reverse = 0;
        for(int i=0;i<9;i++){
            for(int j=0;j<i;j++){
                if(data[j]>data[i])
                    reverse++;
                if(target.getData()[j]>target.getData()[i])
                    reverse++;
            }
        }
        if(reverse % 2 == 0)
            return true;
        return false;
    }



}

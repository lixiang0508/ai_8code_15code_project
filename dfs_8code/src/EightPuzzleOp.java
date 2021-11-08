/**
 * @author zlx
 * @create 2021-10-23 5:51 下午
 */
public class EightPuzzleOp {
     // 0 1 2 3上下左右   x是0的位置
    public static boolean canMove(int x,int dir) {
         if(x<3 && dir==0)  return false;
         if(x>5 && dir==1)     return false;
         if(x==0 || x==3 || x==6) {
             if(dir==2)       return false;
         }
         if(x==2 || x==5 || x==8) {
             if(dir==3)      return false;
         }
         return true;
    }
    public static EightPuzzle movePos(EightPuzzle ep, int dir) {
        EightPuzzle newEp= ep.deepCopy();

        if(dir==0)  {
            /*
            int temp= newEp.getData()[newEp.getZeroPos()] ;
            newEp.data[newEp.getZeroPos()] =newEp.data[newEp.getZeroPos()-3];
            newEp.data[newEp.getZeroPos()-3]=temp;*/
            //向上移动
            int p= ep.getPosition();
            newEp.getData()[p] =ep.getData()[p-3]  ;
            newEp.getData()[p-3] =ep.getData()[p] ;

        }
        //向下移动
        else if(dir==1) {
            /*
            int temp= newEp.getData()[newEp.getZeroPos()] ;
            newEp.data[newEp.getZeroPos()] =newEp.data[newEp.getZeroPos()+3];
            newEp.data[newEp.getZeroPos()+3]=temp; */
            int p= ep.getPosition();
            newEp.getData()[p] =ep.getData()[p+3]  ;
            newEp.getData()[p+3] =ep.getData()[p] ;

        }
        //向左移动
        else if(dir==2) {
            /*
            int temp= newEp.getData()[newEp.getZeroPos()] ;
            newEp.data[newEp.getZeroPos()] =newEp.data[newEp.getZeroPos()-1];
            newEp.data[newEp.getZeroPos()-1]=temp;*/
            int p= ep.getPosition();
            newEp.getData()[p] =ep.getData()[p-1]  ;
            newEp.getData()[p-1] =ep.getData()[p] ;

        }
        else if(dir==3) { //向右移动
            /*
            int temp= newEp.getData()[newEp.getZeroPos()] ;
            newEp.data[newEp.getZeroPos()] =newEp.data[newEp.getZeroPos()+1];
            newEp.data[newEp.getZeroPos()+1]=temp;  */
            int p= ep.getPosition();
            newEp.getData()[p] =ep.getData()[p+1]  ;
            newEp.getData()[p+1] =ep.getData()[p] ;
        }

        return newEp;
    }
}

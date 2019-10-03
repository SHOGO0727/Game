import java.util.*;

public class blackjuck{
    public static void main(String[] args) {
      Scanner sc = new Scanner(System.in);

      //カードを用意
      int[][] card = new int[4][13];

      //点数を代入
      for(int i=0;i<4;i++){
        for(int j=0;j<13;j++){
          if(j>=9){card[i][j] = 10;}
          else{card[i][j] = j+1;}
          //System.out.print(card[i][j]+" ");
        }
        //System.out.println("");
      }

      //手札の格納(n枚)
      int n = 20;
      int[] dealer = new int[n];
      int[] player = new int[n];

      //カード選択の変数
      int select4;
      int select13;

      //ディーラの初期手札
      int cntd=0;
      while(true){
        select4 = (int)((Math.random()*4));
        select13 = (int)((Math.random()*13));
        if(card[select4][select13]!=0){ //同じカードの選択回避
          dealer[cntd] = card[select4][select13];
          card[select4][select13] = 0;
          cntd++;
        }
        if(cntd==2){break;}
      }
      //合計値格納
      int sumd = dealer[0]+dealer[1];

      //プレイヤーの初期手札
      int cntp=0;
      while(true){
        select4 = (int)((Math.random()*4));
        select13 = (int)((Math.random()*13));
        if(card[select4][select13]!=0){ //同じカードの選択回避
          player[cntp] = card[select4][select13];
          card[select4][select13] = 0;
          cntp++;
        }
        if(cntp==2){break;}
      }
      //合計値格納
      int sump = player[0]+player[1];

      System.out.print("dealerの手札：");
      System.out.println(dealer[0]+" ?");

      System.out.print("playerの手札：");
      for(int i=0;i<n;i++){
        if(player[i]!=0){
          System.out.print(player[i]+" ");
        }
      }
      System.out.println("");

      while(true){
        if(sump>21){
          System.out.println("手札の合計が21を超えました。");
          break;
        }
        String move;
        while(true){
          System.out.print("次の行動を入力してください。（ヒット：h, スタンド:s）：");
          move = sc.next();
          if(move.equals("s") || move.equals("h")){break;}
        }
        if(move.equals("s")){break;}
        if(move.equals("h")){
          while(true){
            select4 = (int)((Math.random()*4));
            select13 = (int)((Math.random()*13));
            if(card[select4][select13]!=0){
              player[cntp] = card[select4][select13];
              card[select4][select13] = 0;
              sump += player[cntp];
              cntp++;
              break;
            }
          }
        }
        System.out.print("playerの手札：");
        for(int i=0;i<n;i++){
          if(player[i]!=0){
            System.out.print(player[i]+" ");
          }
        }
        System.out.println("");
      }

      System.out.print("dealerの手札：");
      for(int i=0;i<n;i++){
        if(dealer[i]!=0){
          System.out.print(dealer[i]+" ");
        }
      }
      System.out.println("");

      //dealerは17以上になるまで追加
      while(sumd<17){
        select4 = (int)((Math.random()*4));
        select13 = (int)((Math.random()*13));
        if(card[select4][select13]!=0){
          dealer[cntd] = card[select4][select13];
          card[select4][select13] = 0;
          sumd += dealer[cntd];
          cntd++;
          System.out.print("dealerの手札：");
          for(int i=0;i<n;i++){
            if(dealer[i]!=0){
              System.out.print(dealer[i]+" ");
            }
          }
          System.out.println("");
        }
      }

      System.out.print("playerの手札：");
      for(int i=0;i<n;i++){
        if(player[i]!=0){
          System.out.print(player[i]+" ");
        }
      }
      System.out.println("");

      System.out.println("delaerの合計値："+sumd);
      System.out.println("playerの合計値："+sump);

      System.out.print("判定：");
      //勝敗判定
      if(sumd>21 && sump>21){
        System.out.println("引き分け");
      }
      else if(sumd>21){
        System.out.println("勝ち");
      }
      else if(sump>21){
        System.out.println("負け");
      }
      else{
        if(sumd>sump){
          System.out.println("負け");
        }
        if(sumd<sump){
          System.out.println("勝ち");
        }
        if(sumd==sump){
          System.out.println("引き分け");
        }
      }



/*カードの選択状況
      for(int i=0;i<4;i++){
        for(int j=0;j<13;j++){
          System.out.print(card[i][j]+" ");
        }
        System.out.println("");
      }
*/

    }
}
